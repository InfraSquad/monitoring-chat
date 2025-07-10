package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ✅ Hàm lấy danh sách bảng từ H2 database
    private List<String> getH2TableNames() {
        try {
            return jdbcTemplate.queryForList(
                    "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'",
                    String.class
            );
        } catch (Exception e) {
            return List.of();
        }
    }

    @GetMapping
    public String queryPage(Model model) {
        model.addAttribute("sql", "");
        model.addAttribute("headers", Collections.emptyList());
        model.addAttribute("result", Collections.emptyList());
        model.addAttribute("rowCount", 0);
        model.addAttribute("executionTimeMs", 0);
        model.addAttribute("error", null);
        model.addAttribute("tables", getH2TableNames()); // ✅ Gửi danh sách bảng qua view
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
            model.addAttribute("headers", List.of());
            model.addAttribute("result", List.of());
            model.addAttribute("rowCount", 0);
            model.addAttribute("executionTimeMs", 0);
        }

        model.addAttribute("tables", getH2TableNames()); // ✅ Đảm bảo gửi lại danh sách bảng sau POST
        model.addAttribute("view", "view/query");
        return "layout"; // Load layout.html
    }
}
