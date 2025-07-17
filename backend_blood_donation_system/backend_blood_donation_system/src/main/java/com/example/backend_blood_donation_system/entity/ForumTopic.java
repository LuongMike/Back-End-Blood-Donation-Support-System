package com.example.backend_blood_donation_system.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
    @Table(name = "forum_topic")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ForumTopic {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Column(columnDefinition = "nvarchar(max)")
        private String description;

        @Column(nullable = false)
        private Long createdBy;

        @Column(nullable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<ForumPost> posts = new ArrayList<>();
    }


