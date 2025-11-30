package com.antiplag.gateway.model;

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
        String status,
        String failureReason
) {
}
