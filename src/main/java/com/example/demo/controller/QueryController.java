package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String queryPage(Model model) {
        model.addAttribute("sql", "");
        model.addAttribute("resultHeaders", Collections.emptyList());
        model.addAttribute("resultRows", Collections.emptyList());
        model.addAttribute("error", null);
        model.addAttribute("view", "view/query");
        return "layout"; // Load layout.html
    }

    @PostMapping("/execute")
    public String executeQuery(@RequestParam("sql") String sql, Model model) {
        long start = System.currentTimeMillis();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            long end = System.currentTimeMillis();

            if (rows.isEmpty()) {
                model.addAttribute("headers", List.of());
                model.addAttribute("result", List.of());
            } else {
                model.addAttribute("headers", new ArrayList<>(rows.get(0).keySet()));
                model.addAttribute("result", rows.stream()
                        .map(row -> new ArrayList<>(row.values()))
                        .collect(Collectors.toList()));
            }

            model.addAttribute("rowCount", rows.size());
            model.addAttribute("executionTimeMs", end - start);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("view", "view/query");
        return "layout"; // Load layout.html
    }
}
