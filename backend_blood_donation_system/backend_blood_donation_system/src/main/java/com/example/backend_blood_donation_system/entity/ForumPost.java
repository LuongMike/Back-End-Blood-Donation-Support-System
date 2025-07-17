package com.example.backend_blood_donation_system.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "forum_post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id", foreignKey = @ForeignKey(name = "FK_forumpost_forumtopic"))
    private ForumTopic topic;

    @Column(columnDefinition = "nvarchar(max)")
    private String content;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private Boolean visible = true;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

