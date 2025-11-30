package com.antiplag.filestore.model;

import java.time.Instant;
import java.util.UUID;

public class FileMetadata {
    private final UUID id;
    private final String originalName;
    private final String storedPath;
    private final long size;
    private final String contentType;
    private final Instant uploadedAt;

    public FileMetadata(UUID id, String originalName, String storedPath, long size, String contentType, Instant uploadedAt) {
        this.id = id;
        this.originalName = originalName;
        this.storedPath = storedPath;
        this.size = size;
        this.contentType = contentType;
        this.uploadedAt = uploadedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getStoredPath() {
        return storedPath;
    }

    public long getSize() {
        return size;
    }

    public String getContentType() {
        return contentType;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }
}
