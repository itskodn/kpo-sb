package com.antiplag.gateway.model;

import java.time.Instant;
import java.util.UUID;

public record FileUploadResponse(
        UUID id,
        String originalName,
        String storedPath,
        long size,
        String contentType,
        Instant uploadedAt
) {
}
