package com.example.backend_blood_donation_system.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;



@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;




    public Optional<User> login(String login, String password) {
        Optional<User> userOpt = userRepository.findByUsernameOrEmail(login);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (password.equals(user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}