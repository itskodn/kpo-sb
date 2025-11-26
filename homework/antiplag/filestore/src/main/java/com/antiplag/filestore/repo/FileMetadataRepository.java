package com.antiplag.filestore.repo;

import com.antiplag.filestore.model.FileMetadata;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileMetadataRepository {
    private final Map<UUID, FileMetadata> storage = new ConcurrentHashMap<>();

    public FileMetadata save(FileMetadata metadata) {
        storage.put(metadata.getId(), metadata);
        return metadata;
    }

    public Optional<FileMetadata> find(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }
}
