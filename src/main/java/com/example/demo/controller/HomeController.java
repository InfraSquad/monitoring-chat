package com.example.demo.controller;

import com.example.demo.model.UrlStatus;
import com.example.demo.repository.UrlStatusRepository;
import com.example.demo.service.HealthCheckService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final UrlStatusRepository repo;
    private final HealthCheckService healthService;

    public HomeController(UrlStatusRepository repo, HealthCheckService healthService) {
        this.repo = repo;
        this.healthService = healthService;
    }

    @GetMapping("/")
    public String home(Model model) {
        healthService.checkAll();
        model.addAttribute("urls", repo.findAll());
        return "home";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size) {
        healthService.checkAll();
        Page<UrlStatus> urlPage = repo.findAll(PageRequest.of(page, size));
        model.addAttribute("urlPage", urlPage);
        return "dashboard";
    }

    @GetMapping("/raw")
    public String showRaw(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size) {
        Page<UrlStatus> urlPage = repo.findAll(PageRequest.of(page, size));
        model.addAttribute("urlPage", urlPage);
        return "raw";
    }
}
