package com.example.demo.repository;

import com.example.demo.model.FeignLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeignLogRepository extends JpaRepository<FeignLog, Long> {
    Page<FeignLog> findAll(Pageable pageable);
    Page<FeignLog> findByContentContainingIgnoreCase(String content, Pageable pageable);
    @Query("SELECT f FROM FeignLog f WHERE f.content LIKE %:keyword%")
    Page<FeignLog> searchByContent(@Param("keyword") String keyword, Pageable pageable);
}
