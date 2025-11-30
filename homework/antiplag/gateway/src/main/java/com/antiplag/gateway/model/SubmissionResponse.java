package com.antiplag.gateway.model;

public record SubmissionResponse(
        FileUploadResponse file,
        ReportResponse report
) {
}
