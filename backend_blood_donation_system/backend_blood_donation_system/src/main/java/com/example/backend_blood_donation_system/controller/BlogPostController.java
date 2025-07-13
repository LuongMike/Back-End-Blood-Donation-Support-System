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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogPostController {
    private final BlogPostService blogPostService;

    @PreAuthorize("hasAuthority('STAFF')") // Chặn role không phải STAFF
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody BlogPostRequest request,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        blogPostService.createPost(request, userDetails.getUser());
        return ResponseEntity.ok("Blog post created");
    }
}
