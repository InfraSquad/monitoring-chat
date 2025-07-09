package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    public List<Comment> findByTaskId(Long taskId) {
        return commentRepository.findByTaskIdOrderByCreatedAtDesc(taskId);
    }

    public void addComment(Long taskId, String author, String content) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setAuthor(author);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }
}
