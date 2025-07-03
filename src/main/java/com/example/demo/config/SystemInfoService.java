package com.example.demo.config;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SystemInfoService {

    private final Instant startTime = Instant.now();

    public Map<String, String> getSystemInfo() {
        Map<String, String> info = new HashMap<>();

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();

        info.put("OS Name", osBean.getName());
        info.put("OS Version", osBean.getVersion());
        info.put("Available Processors", String.valueOf(osBean.getAvailableProcessors()));
        info.put("Architecture", osBean.getArch());

        info.put("Java Version", System.getProperty("java.version"));
        info.put("Java Vendor", System.getProperty("java.vendor"));

        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);

        info.put("Max Memory (MB)", String.valueOf(maxMemory));
        info.put("Total Memory (MB)", String.valueOf(totalMemory));
        info.put("Free Memory (MB)", String.valueOf(freeMemory));
        info.put("Used Memory (MB)", String.valueOf(totalMemory - freeMemory));

        info.put("System Uptime", formatDuration(Duration.between(startTime, Instant.now())));

        return info;
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        return String.format("%02dh %02dm %02ds", hours, minutes, seconds);
    }
}
