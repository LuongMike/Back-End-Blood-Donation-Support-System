package com.example.backend_blood_donation_system.controller;


import com.example.backend_blood_donation_system.dto.BlogPostRequest;
import com.example.backend_blood_donation_system.security.CustomUserDetails;
import com.example.backend_blood_donation_system.service.BlogPostService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
}
