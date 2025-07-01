package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addRequestUriToModel(HttpServletRequest request, Model model) {
        model.addAttribute("requestUri", request.getRequestURI());
    }
}
