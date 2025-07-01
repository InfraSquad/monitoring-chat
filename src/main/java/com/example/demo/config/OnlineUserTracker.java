package com.example.demo.config;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineUserTracker {

    // Map<room, Map<sessionId, nickname>>
    private final Map<String, Map<String, String>> roomUserMap = new ConcurrentHashMap<>();

    // Map<sessionId, RoomNickname>
    private final Map<String, RoomNickname> sessionInfoMap = new ConcurrentHashMap<>();

    public void userJoined(String sessionId, String room, String nickname) {
        roomUserMap.computeIfAbsent(room, r -> new ConcurrentHashMap<>()).put(sessionId, nickname);
        sessionInfoMap.put(sessionId, new RoomNickname(room, nickname));
    }

    public void userLeft(String sessionId) {
        RoomNickname info = sessionInfoMap.remove(sessionId);
        if (info != null) {
            Map<String, String> userMap = roomUserMap.get(info.getRoom());
            if (userMap != null) {
                userMap.remove(sessionId);
                if (userMap.isEmpty()) {
                    roomUserMap.remove(info.getRoom());
                }
            }
        }
    }

    public Set<String> getUsers(String room) {
        return new HashSet<>(roomUserMap.getOrDefault(room, Collections.emptyMap()).values());
    }

    public int getUserCount(String room) {
        return getUsers(room).size();
    }

    public RoomNickname getUserInfo(String sessionId) {
        return sessionInfoMap.get(sessionId);
    }

    public static class RoomNickname {
        private final String room;
        private final String nickname;

        public RoomNickname(String room, String nickname) {
            this.room = room;
            this.nickname = nickname;
        }

        public String getRoom() {
            return room;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
