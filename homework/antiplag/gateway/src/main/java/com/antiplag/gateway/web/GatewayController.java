package com.antiplag.gateway.web;

import com.antiplag.gateway.model.FileUploadResponse;
import com.antiplag.gateway.model.ReportResponse;
import com.antiplag.gateway.model.SubmissionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class GatewayController {
    private final RestTemplate restTemplate;
    private final String filestoreUrl;
    private final String analysisUrl;

    public GatewayController(RestTemplate restTemplate,
                             @Value("${filestore.url:http://filestore:8081}") String filestoreUrl,
                             @Value("${analysis.url:http://analysis:8082}") String analysisUrl) {
        this.restTemplate = restTemplate;
        this.filestoreUrl = filestoreUrl;
        this.analysisUrl = analysisUrl;
    }

    @PostMapping(path = "/works", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SubmissionResponse submitWork(@RequestPart("file") MultipartFile file,
                                         @RequestParam("studentName") String studentName,
                                         @RequestParam("assignmentId") String assignmentId) {
        FileUploadResponse uploaded = uploadToFileStore(file);
        ReportResponse report = requestReport(uploaded.id(), studentName, assignmentId);
        return new SubmissionResponse(uploaded, report);
    }

    @GetMapping("/reports")
    public List<?> listReports(@RequestParam(name = "assignmentId", required = false) String assignmentId) {
        String url = analysisUrl + "/reports";
        if (assignmentId != null) {
            url += "?assignmentId=" + assignmentId;
        }
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        return response.getBody();
    }

    @GetMapping("/reports/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable UUID id) {
        ResponseEntity<ReportResponse> response = restTemplate.getForEntity(analysisUrl + "/reports/" + id, ReportResponse.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/reports/{id}/wordcloud")
    public ResponseEntity<Map> getWordCloud(@PathVariable UUID id) {
        ResponseEntity<Map> response = restTemplate.getForEntity(analysisUrl + "/reports/" + id + "/wordcloud", Map.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID id) {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(filestoreUrl + "/files/" + id, byte[].class);
        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }

    private FileUploadResponse uploadToFileStore(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(getBytes(file)) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
        body.add("file", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<FileUploadResponse> response = restTemplate.postForEntity(
                filestoreUrl + "/files",
                requestEntity,
                FileUploadResponse.class
        );
        return response.getBody();
    }

    private ReportResponse requestReport(UUID fileId, String studentName, String assignmentId) {
        Map<String, Object> payload = Map.of(
                "fileId", fileId,
                "studentName", studentName,
                "assignmentId", assignmentId,
                "submittedAt", Instant.now().toString()
        );
        ResponseEntity<ReportResponse> response = restTemplate.postForEntity(
                analysisUrl + "/reports",
                payload,
                ReportResponse.class
        );
        return response.getBody();
    }

    private byte[] getBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot read uploaded file", e);
        }
    }
}
