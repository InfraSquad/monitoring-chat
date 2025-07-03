package com.example.demo.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_status")
public class UrlStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private boolean reachable;
    private String type;
    private String description;
    private LocalDateTime lastChecked;
    private String responseTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public boolean isReachable() { return reachable; }
    public void setReachable(boolean reachable) { this.reachable = reachable; }

    public LocalDateTime getLastChecked() { return lastChecked; }
    public void setLastChecked(LocalDateTime lastChecked) { this.lastChecked = lastChecked; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getResponseTime() { return responseTime; }
    public void setResponseTime(String responseTime) { this.responseTime = responseTime; }
}
