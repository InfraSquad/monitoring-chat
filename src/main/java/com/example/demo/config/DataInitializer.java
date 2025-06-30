package com.example.demo.config;

import com.example.demo.model.FeignLog;
import com.example.demo.model.UrlStatus;
import com.example.demo.repository.FeignLogRepository;
import com.example.demo.repository.UrlStatusRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private UrlStatusRepository repository;
    @Autowired
    private FeignLogRepository feignLogRepo;

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            List<String> urls = List.of(
                    "https://www.google.com.vn",
                    "https://github.com",
                    "https://spring.io",
                    "https://nonexistent.example.com"
            );

            for (String url : urls) {
                UrlStatus status = new UrlStatus();
                status.setUrl(url);
                status.setReachable(false); // mặc định chưa kiểm tra nên false
                status.setLastChecked(LocalDateTime.now());
                repository.save(status);
            }
        }
    }

    @PostConstruct
    public void initLogs() {
        if (feignLogRepo.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                FeignLog log = new FeignLog();
                log.setContent("This is a simulated log content with details: #" + i + " - " + "A".repeat(200));
                log.setTime(LocalDateTime.now().minusMinutes(i * 3));
                feignLogRepo.save(log);
            }
        }
    }
}
