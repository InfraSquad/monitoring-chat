package com.example.demo.repository;

import com.example.demo.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnippetRepo extends JpaRepository<Snippet, Long> {}
