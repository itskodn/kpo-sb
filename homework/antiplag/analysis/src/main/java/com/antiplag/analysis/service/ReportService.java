package com.antiplag.analysis.service;

import com.antiplag.analysis.model.AnalyzeRequest;
import com.antiplag.analysis.model.Report;
import com.antiplag.analysis.model.ReportStatus;
import com.antiplag.analysis.repo.ReportRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository repository;
    private final FileStoreClient fileStoreClient;

    public ReportService(ReportRepository repository, FileStoreClient fileStoreClient) {
        this.repository = repository;
        this.fileStoreClient = fileStoreClient;
    }

    public Report analyze(AnalyzeRequest request) {
        Instant submittedAt = Optional.ofNullable(request.getSubmittedAt()).orElseGet(Instant::now);
        byte[] content;
        try {
            content = fileStoreClient.fetchFile(request.getFileId());
        } catch (Exception e) {
            return repository.save(new Report(
                    UUID.randomUUID(),
                    request.getFileId(),
                    request.getAssignmentId(),
                    request.getStudentName(),
                    submittedAt,
                    false,
                    null,
                    null,
                    ReportStatus.FAILED,
                    "Cannot fetch file: " + e.getMessage()
            ));
        }

        String hash;
        try {
            hash = sha256(content);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }

        Optional<Report> previousMatch = repository.findAll().stream()
                .filter(r -> r.getAssignmentId().equals(request.getAssignmentId()))
                .filter(r -> !r.getStudentName().equalsIgnoreCase(request.getStudentName()))
                .filter(r -> hash.equals(r.getHash()))
                .min(Comparator.comparing(Report::getSubmittedAt));

        boolean plagiarized = previousMatch.isPresent();
        String plagiarismSource = previousMatch.map(r -> r.getStudentName() + " (" + r.getSubmittedAt() + ")").orElse(null);

        Report report = new Report(
                UUID.randomUUID(),
                request.getFileId(),
                request.getAssignmentId(),
                request.getStudentName(),
                submittedAt,
                plagiarized,
                plagiarismSource,
                hash,
                ReportStatus.COMPLETED,
                null
        );
        repository.save(report);
        return report;
    }

    public List<Report> list(String assignmentId) {
        return repository.findAll().stream()
                .filter(r -> assignmentId == null || assignmentId.equals(r.getAssignmentId()))
                .sorted(Comparator.comparing(Report::getSubmittedAt))
                .collect(Collectors.toList());
    }

    public Optional<Report> get(UUID id) {
        return repository.find(id);
    }

    private String sha256(byte[] content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(content);
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String buildWordCloudUrl(UUID reportId) {
        Optional<Report> reportOpt = repository.find(reportId);
        if (reportOpt.isEmpty()) {
            return null;
        }
        Report report = reportOpt.get();
        byte[] content = fileStoreClient.fetchFile(report.getFileId());
        String text = new String(content, StandardCharsets.UTF_8);
        Map<String, Integer> frequencies = countWords(text);
        if (frequencies.isEmpty()) {
            return null;
        }
        String repeatedWords = frequencies.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(20)
                .map(entry -> entry.getKey().repeat(Math.max(1, entry.getValue())))
                .collect(Collectors.joining(" "));
        return "https://quickchart.io/wordcloud?text=" + urlEncode(repeatedWords);
    }

    private Map<String, Integer> countWords(String text) {
        Map<String, Integer> freq = new HashMap<>();
        String[] words = text.toLowerCase(Locale.ROOT).split("[^\\p{L}]+");
        for (String word : words) {
            if (word.length() < 3) {
                continue;
            }
            freq.merge(word, 1, Integer::sum);
        }
        return freq;
    }

    private String urlEncode(String value) {
        return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
