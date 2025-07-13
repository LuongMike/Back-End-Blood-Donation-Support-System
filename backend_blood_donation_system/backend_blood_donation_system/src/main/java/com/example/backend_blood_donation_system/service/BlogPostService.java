package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.BlogPostRequest;
import com.example.backend_blood_donation_system.entity.BlogPost;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public void createPost(BlogPostRequest request, User author) {
        BlogPost post = new BlogPost();
        post.setAuthor(author);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImage(request.getImage());
        post.setType(request.getType());
        post.setCreatedAt(LocalDateTime.now());
        blogPostRepository.save(post);
    }
}