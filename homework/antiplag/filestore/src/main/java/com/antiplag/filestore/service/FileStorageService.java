package com.antiplag.filestore.service;

import com.antiplag.filestore.model.FileMetadata;
import com.antiplag.filestore.repo.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path baseDir;
    private final FileMetadataRepository repository;

    public FileStorageService(@Value("${filestore.base-path:data/files}") String basePath,
                              FileMetadataRepository repository) throws IOException {
        this.baseDir = Path.of(basePath).toAbsolutePath();
        this.repository = repository;
        Files.createDirectories(this.baseDir);
    }

    public FileMetadata store(MultipartFile file) throws IOException {
        UUID id = UUID.randomUUID();
        String filename = Optional.ofNullable(file.getOriginalFilename()).orElse("uploaded");
        String contentType = Optional.ofNullable(file.getContentType()).orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        Path destination = baseDir.resolve(id.toString());
        Files.copy(file.getInputStream(), destination);

        FileMetadata metadata = new FileMetadata(
                id,
                filename,
                destination.toString(),
                file.getSize(),
                contentType,
                Instant.now()
        );
        repository.save(metadata);
        return metadata;
    }

    public Optional<FileMetadata> getMetadata(UUID id) {
        return repository.find(id);
    }

    public Optional<Resource> load(UUID id) {
        return repository.find(id).map(data -> new FileSystemResource(data.getStoredPath()));
    }
}
