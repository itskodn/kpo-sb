package com.antiplag.analysis.repo;

import com.antiplag.analysis.model.Report;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ReportRepository {
    private final Map<UUID, Report> storage = new ConcurrentHashMap<>();

    public Report save(Report report) {
        storage.put(report.getId(), report);
        return report;
    }

    public Optional<Report> find(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Collection<Report> findAll() {
        return storage.values();
    }
}
