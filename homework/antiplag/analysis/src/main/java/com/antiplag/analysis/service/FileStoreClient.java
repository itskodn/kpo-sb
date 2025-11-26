package com.antiplag.analysis.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class FileStoreClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public FileStoreClient(RestTemplate restTemplate,
                           @Value("${filestore.url:http://filestore:8081}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public byte[] fetchFile(UUID id) {
        ResponseEntity<byte[]> response = restTemplate.getForEntity(baseUrl + "/files/" + id, byte[].class);
        return response.getBody();
    }
}
