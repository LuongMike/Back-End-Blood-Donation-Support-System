package com.example.backend_blood_donation_system.dto;

import com.example.backend_blood_donation_system.enums.PostType;
import lombok.Data;

@Data
public class BlogPostRequest {
    private String title;
    private String content;
    private String image;
    private PostType type;
}
