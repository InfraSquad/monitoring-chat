package com.example.demo.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PortFinderWindows {

    public static Set<String> getPortsByPid(int pid) {
        Set<String> ports = new HashSet<>();
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "netstat -aon");
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("LISTENING") && line.trim().endsWith(String.valueOf(pid))) {
                    String[] tokens = line.trim().split("\\s+");
                    if (tokens.length >= 5) {
                        String localAddress = tokens[1]; // ví dụ: 0.0.0.0:8080
                        String port = localAddress.substring(localAddress.lastIndexOf(':') + 1);
                        ports.add(port);
                    }
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ports;
    }
}
