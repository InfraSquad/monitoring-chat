package com.example.demo.controller;

import com.example.demo.model.ChatHistory;
import com.example.demo.model.ChatMessage;
import com.example.demo.repository.ChatHistoryRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatHistoryRepository chatRepo;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatHistoryRepository chatRepo) {
        this.messagingTemplate = messagingTemplate;
        this.chatRepo = chatRepo;
    }

    @MessageMapping("/chat/{room}")
    public void sendMessage(@DestinationVariable String room, ChatHistory message) {
        message.setTimestamp(LocalDateTime.now());
        chatRepo.save(message);
        messagingTemplate.convertAndSend("/topic/" + room, message);
    }

    // Khi load chat.html, gửi lịch sử tin nhắn
    @GetMapping("/chat")
    public String chatPage(@RequestParam(required = false) String room, Model model) {
        if (room != null && !room.isEmpty()) {
            List<ChatHistory> history = chatRepo.findByRoomOrderByTimestampAsc(room);
            model.addAttribute("history", history);
            model.addAttribute("room", room);
        } else {
            model.addAttribute("room", ""); // để client tự lấy từ localStorage
        }
        return "chat";
    }

}
