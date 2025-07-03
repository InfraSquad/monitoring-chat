package com.example.demo.controller;

import com.example.demo.config.SystemInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemInfoController {

    private final SystemInfoService systemInfoService;

    public SystemInfoController(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @GetMapping("/system-info")
    public String showSystemInfo(Model model) {
        model.addAttribute("systemInfo", systemInfoService.getSystemInfo());
        model.addAttribute("view", "view/system-info");
        return "layout";
    }
}
