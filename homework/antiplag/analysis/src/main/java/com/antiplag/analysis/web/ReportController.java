package com.antiplag.analysis.web;

import com.antiplag.analysis.model.AnalyzeRequest;
import com.antiplag.analysis.model.Report;
import com.antiplag.analysis.model.ReportResponse;
import com.antiplag.analysis.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ReportResponse analyze(@Valid @RequestBody AnalyzeRequest request) {
        Report report = reportService.analyze(request);
        return ReportResponse.from(report);
    }

    @GetMapping
    public List<ReportResponse> list(@RequestParam(name = "assignmentId", required = false) String assignmentId) {
        return reportService.list(assignmentId)
                .stream()
                .map(ReportResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> get(@PathVariable UUID id) {
        Optional<Report> reportOpt = reportService.get(id);
        return reportOpt.map(report -> ResponseEntity.ok(ReportResponse.from(report)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/wordcloud")
    public ResponseEntity<?> wordCloud(@PathVariable UUID id) {
        String url = reportService.buildWordCloudUrl(id);
        if (url == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(java.util.Map.of("url", url));
    }
}
