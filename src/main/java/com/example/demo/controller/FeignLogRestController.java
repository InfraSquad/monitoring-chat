package com.example.demo.controller;

import com.example.demo.model.FeignLog;
import com.example.demo.repository.FeignLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/feign-logs")
public class FeignLogRestController {

    private final FeignLogRepository repository;

    public FeignLogRestController(FeignLogRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<String> addLog(@RequestBody LogRequest logRequest) {
        if (logRequest == null || logRequest.getContent() == null || logRequest.getContent().isBlank()) {
            return ResponseEntity.badRequest().body("Content is required");
        }

        FeignLog log = new FeignLog();
        log.setContent(logRequest.getContent());
        log.setTime(LocalDateTime.now());
        repository.save(log);

        return ResponseEntity.ok("Log saved successfully");
    }

    // DTO
    public static class LogRequest {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
