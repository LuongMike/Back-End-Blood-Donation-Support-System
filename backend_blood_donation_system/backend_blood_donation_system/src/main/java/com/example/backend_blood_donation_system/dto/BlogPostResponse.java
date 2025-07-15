package com.example.backend_blood_donation_system.dto;


import com.example.backend_blood_donation_system.enums.PostType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogPostResponse {
    private Integer postId;
    private String title;
    private String content;
    private String image;
    private PostType type;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
