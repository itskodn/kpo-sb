package com.antiplag.filestore.web;

import com.antiplag.filestore.model.FileMetadata;
import com.antiplag.filestore.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestPart("file") MultipartFile file) throws IOException {
        FileMetadata metadata = storageService.store(file);
        return ResponseEntity.ok(Map.of(
                "id", metadata.getId(),
                "originalName", metadata.getOriginalName(),
                "storedPath", metadata.getStoredPath(),
                "size", metadata.getSize(),
                "contentType", metadata.getContentType(),
                "uploadedAt", metadata.getUploadedAt()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        Optional<Resource> resourceOpt = storageService.load(id);
        Optional<FileMetadata> metadataOpt = storageService.getMetadata(id);

        if (resourceOpt.isEmpty() || metadataOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FileMetadata metadata = metadataOpt.get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getOriginalName() + "\"")
                .body(resourceOpt.get());
    }
}
