package com.example.demo.model;

import com.example.demo.config.PortFinderWindows;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JavaProcessInfo {
    public long pid;
    public String command;
    public String name;              // Tên tiến trình, ví dụ app.jar
    public String fullCommand;
    public String startTime;
    public String uptime;
    public Set<String> ports = new HashSet<>(); // Thêm danh sách port


    public static JavaProcessInfo from(ProcessHandle handle) {
        var info = handle.info();
        JavaProcessInfo p = new JavaProcessInfo();
        p.pid = handle.pid();
        p.name = extractProcessName(handle.info().command().orElse(""));
        p.fullCommand = info.commandLine().orElse("N/A");
        p.command = p.fullCommand.length() > 50 ? p.fullCommand.substring(0, 50) + "..." : p.fullCommand;
        p.ports = PortFinderWindows.getPortsByPid((int) handle.pid()); // Gọi hàm tìm port

        info.startInstant().ifPresent(i -> {
            p.startTime = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
                    .withZone(ZoneId.systemDefault()).format(i);
            Duration duration = Duration.between(i, Instant.now());
            p.uptime = formatDuration(duration);
        });
        return p;
    }

    private static String formatDuration(Duration d) {
        long hours = d.toHours();
        long minutes = d.toMinutesPart();
        return hours + "h " + minutes + "m";
    }

    private static String extractProcessName(String fullPath) {
        if (fullPath == null || fullPath.isEmpty()) return "Unknown";
        return fullPath.substring(fullPath.lastIndexOf("/") + 1);
    }
}
