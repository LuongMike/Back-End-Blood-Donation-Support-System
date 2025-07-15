package com.example.backend_blood_donation_system.controller;

import com.example.backend_blood_donation_system.entity.ForumPost;
import com.example.backend_blood_donation_system.entity.ForumTopic;
import com.example.backend_blood_donation_system.service.ForumPostService;
import com.example.backend_blood_donation_system.service.ForumTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ForumController {

    private final ForumTopicService topicService;
    private final ForumPostService postService;

    // ========== PUBLIC ==========
    @GetMapping("/forum/topics")
    public List<ForumTopic> getAllTopics() {
        return topicService.getAllTopics();
    }
    @GetMapping("/forum/topics/{id}/posts")
    public List<ForumPost> getVisiblePosts(@PathVariable Long id) {
        return postService.getVisiblePosts(id);
    }

}