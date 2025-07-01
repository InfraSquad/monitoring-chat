package com.example.demo.repository;

import com.example.demo.model.UrlStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlStatusRepository extends JpaRepository<UrlStatus, Long> {
    Page<UrlStatus> findAll(Pageable pageable);
    List<UrlStatus> findAllByReachableFalse();
}
