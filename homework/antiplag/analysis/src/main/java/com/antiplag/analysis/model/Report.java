package com.antiplag.analysis.model;

import java.time.Instant;
import java.util.UUID;

public class Report {
    private final UUID id;
    private final UUID fileId;
    private final String assignmentId;
    private final String studentName;
    private final Instant submittedAt;
    private final boolean plagiarized;
    private final String plagiarismSource;
    private final String hash;
    private final ReportStatus status;
    private final String failureReason;

    public Report(UUID id,
                  UUID fileId,
                  String assignmentId,
                  String studentName,
                  Instant submittedAt,
                  boolean plagiarized,
                  String plagiarismSource,
                  String hash,
                  ReportStatus status,
                  String failureReason) {
        this.id = id;
        this.fileId = fileId;
        this.assignmentId = assignmentId;
        this.studentName = studentName;
        this.submittedAt = submittedAt;
        this.plagiarized = plagiarized;
        this.plagiarismSource = plagiarismSource;
        this.hash = hash;
        this.status = status;
        this.failureReason = failureReason;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFileId() {
        return fileId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public boolean isPlagiarized() {
        return plagiarized;
    }

    public String getPlagiarismSource() {
        return plagiarismSource;
    }

    public String getHash() {
        return hash;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
