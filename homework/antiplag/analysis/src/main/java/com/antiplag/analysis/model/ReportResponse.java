package com.antiplag.analysis.model;

import java.time.Instant;
import java.util.UUID;

public record ReportResponse(
        UUID id,
        UUID fileId,
        String assignmentId,
        String studentName,
        Instant submittedAt,
        boolean plagiarized,
        String plagiarismSource,
        ReportStatus status,
        String failureReason
) {
    public static ReportResponse from(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getFileId(),
                report.getAssignmentId(),
                report.getStudentName(),
                report.getSubmittedAt(),
                report.isPlagiarized(),
                report.getPlagiarismSource(),
                report.getStatus(),
                report.getFailureReason()
        );
    }
}
