package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class KeyContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storageKey;

    @Lob // Cho phép lưu nội dung dài
    private String contentText;

    private String filePath;
    private String fileType;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStorageKey() { return storageKey; }
    public void setStorageKey(String storageKey) { this.storageKey = storageKey; }

    public String getContentText() { return contentText; }
    public void setContentText(String contentText) { this.contentText = contentText; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
}