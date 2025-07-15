package com.example.backend_blood_donation_system.service;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.example.backend_blood_donation_system.dto.BlogPostRequest;
import com.example.backend_blood_donation_system.dto.BlogPostResponse;
import com.example.backend_blood_donation_system.entity.BlogPost;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.BlogPostRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.backend_blood_donation_system.enums.PostType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

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
    public List<BlogPostResponse> getAllPosts() {
        List<BlogPost> posts = blogPostRepository.findAll();

        return posts.stream().map(post -> {
            BlogPostResponse dto = new BlogPostResponse();
            dto.setPostId(post.getPostId());
            dto.setTitle(post.getTitle());
            dto.setContent(post.getContent());
            dto.setImage(post.getImage());
            dto.setType(post.getType());
            dto.setAuthorName(post.getAuthor().getFullName()); // giả sử User có fullName
            dto.setCreatedAt(post.getCreatedAt());
            dto.setUpdatedAt(post.getUpdatedAt());
            return dto;
        }).collect(Collectors.toList());
    }
}