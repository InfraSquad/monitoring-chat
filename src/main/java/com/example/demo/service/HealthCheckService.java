package com.example.demo.service;

import com.example.demo.model.UrlStatus;
import com.example.demo.repository.UrlStatusRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthCheckService {
    private final UrlStatusRepository repo;
    private final RestTemplate restTemplate = new RestTemplate();

    public HealthCheckService(UrlStatusRepository repo) {
        this.repo = repo;
    }

    public void checkAll() {
        List<UrlStatus> urls = repo.findAll();
        for (UrlStatus urlStatus : urls) {
            boolean reachable = false;
            try {
                ResponseEntity<String> response = restTemplate.exchange(
                        urlStatus.getUrl(),
                        HttpMethod.GET,
                        null,
                        String.class
                );

                // Chỉ khi status code là 200 mới đánh dấu reachable = true
                if (response.getStatusCode().is2xxSuccessful()) {
                    reachable = true;
                }
            } catch (RestClientException e) {
                // Bất kỳ lỗi nào cũng coi là không reachable
                reachable = false;
            }

            urlStatus.setReachable(reachable);
            urlStatus.setLastChecked(LocalDateTime.now());
            repo.save(urlStatus);
        }
    }
}
