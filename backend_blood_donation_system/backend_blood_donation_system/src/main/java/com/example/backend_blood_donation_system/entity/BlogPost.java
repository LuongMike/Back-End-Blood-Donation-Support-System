package com.example.backend_blood_donation_system.entity;

import com.example.backend_blood_donation_system.enums.PostType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "blog_post")
@Data
@Getter
@Setter
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    private String title;
    private String content;
    private String image;

    @Enumerated(EnumType.STRING)
    private PostType type; // Enum blog, news, guide

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
