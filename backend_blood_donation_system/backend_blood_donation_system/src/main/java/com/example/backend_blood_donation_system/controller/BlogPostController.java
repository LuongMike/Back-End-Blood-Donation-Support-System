package com.example.backend_blood_donation_system.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend_blood_donation_system.dto.BlogPostResponse;
import com.example.backend_blood_donation_system.security.CustomUserDetails;
import com.example.backend_blood_donation_system.service.BlogPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogPostController {
    private final BlogPostService blogPostService;

    @PreAuthorize("hasAuthority('STAFF')")
    @PostMapping("/create")
    public ResponseEntity<?> createPost(
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart("type") String type,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        blogPostService.createPost(title, content, type, image, userDetails.getUser());
        return ResponseEntity.ok("Blog post created");
    }
    @GetMapping("/all")
    public ResponseEntity<List<BlogPostResponse>> getAllPosts() {
        List<BlogPostResponse> posts = blogPostService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PreAuthorize("hasAuthority('STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Integer postId) {
        blogPostService.deletePost(postId);
        return ResponseEntity.ok("Đã xóa bài viết thành công với ID: " + postId);
    }
}
