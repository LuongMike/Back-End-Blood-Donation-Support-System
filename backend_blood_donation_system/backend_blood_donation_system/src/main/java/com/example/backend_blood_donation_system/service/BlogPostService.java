package com.example.backend_blood_donation_system.service;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.example.backend_blood_donation_system.dto.BlogPostRequest;
import com.example.backend_blood_donation_system.entity.BlogPost;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.BlogPostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.backend_blood_donation_system.enums.PostType;

import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public void createPost(String title, String content, String type, MultipartFile image, User author) {
        BlogPost post = new BlogPost();
        post.setAuthor(author);
        post.setTitle(title);
        post.setContent(content);
        post.setType(PostType.valueOf(type.toUpperCase())); // "BLOG", "NEWS"
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        // Nếu có file ảnh thì lưu
        if (image != null && !image.isEmpty()) {
            try {
                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path uploadDir = Paths.get("uploads");
                Files.createDirectories(uploadDir);

                Path filePath = uploadDir.resolve(filename);
                Files.write(filePath, image.getBytes());

                post.setImage("/uploads/" + filename); // đường dẫn frontend có thể dùng
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi lưu ảnh", e);
            }
        }

        blogPostRepository.save(post);
    }
}