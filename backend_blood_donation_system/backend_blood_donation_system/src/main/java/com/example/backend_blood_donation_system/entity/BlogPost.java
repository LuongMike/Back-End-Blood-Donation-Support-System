package com.example.backend_blood_donation_system.entity;

import java.time.LocalDateTime;

import com.example.backend_blood_donation_system.enums.PostType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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

    @Lob
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String title;

    @Lob
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    
    private String image;

    @Enumerated(EnumType.STRING)
    private PostType type; // Enum blog, news, guide

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
