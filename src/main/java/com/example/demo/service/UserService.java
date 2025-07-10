package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllByManagerIsNull() {
        return userRepository.findAllByManagerIsNull();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với id: " + id));
    }

    public List<User> getAllSubordinates(User manager) {
        List<User> result = new ArrayList<>();
        collect(manager, result);
        return result;
    }

    private void collect(User current, List<User> result) {
        for (User u : current.getSubordinates()) {
            result.add(u);
            collect(u, result); // đệ quy tiếp
        }
    }

}
