package com.example.demo.repository;

import com.example.demo.model.KeyContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyContentRepository extends JpaRepository<KeyContent, Long> {
    List<KeyContent> findByStorageKeyContainingIgnoreCase(String key);
}