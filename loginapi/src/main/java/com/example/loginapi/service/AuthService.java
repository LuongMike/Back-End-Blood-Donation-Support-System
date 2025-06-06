package com.example.loginapi.service;
import com.example.loginapi.model.Users;
import com.example.loginapi.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import org.springframework.stereotype.Service;;
@Service
public class AuthService {
    private final UserRepository userRepository;


    private final String CLIENT_ID = "293747276620-oa6fei02169ggforoqusbtbsbe16v1hi.apps.googleusercontent.com";

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users verifyGoogleTokenAndSaveUser(String idTokenString) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");


            // Tìm người dùng theo email, nếu chưa có thì lưu mới
            return userRepository.findByEmail(email)
                    .orElseGet(() ->{
                        String userId = UUID.randomUUID().toString();
                        String createdAt = LocalDateTime.now().toString();
                        Users newUser = new Users(
                                createdAt,         // created_at
                                "",                // address (chưa có)
                                "user",            // role (mặc định user)
                                "",                // phone_number (chưa có)
                                name,              // full_name (từ Google)
                                email,             // mail (từ Google)
                                "",                // gender (chưa có)
                                "",                // password (trống vì dùng Google login)
                                "",                // username (chưa có)
                                userId             // user_id
                        );
                        return userRepository.save(newUser);
                    });




        } else {
            throw new Exception("Token không hợp lệ");
        }
    }


}
