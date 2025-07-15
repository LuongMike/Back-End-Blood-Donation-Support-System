package com.example.backend_blood_donation_system.controller;

import com.example.backend_blood_donation_system.dto.ForumPostDTO;
import com.example.backend_blood_donation_system.dto.ForumTopicDTO;
import com.example.backend_blood_donation_system.entity.ForumPost;
import com.example.backend_blood_donation_system.entity.ForumTopic;
import com.example.backend_blood_donation_system.service.ForumPostService;
import com.example.backend_blood_donation_system.service.ForumTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    // ========== MEMBER ==========
    @PostMapping("/member/forum/posts")
    public ForumPost createPost(@RequestBody ForumPostDTO dto) {
        return postService.createPost(dto);
    }
    // ========== ADMIN ==========
    @PostMapping("/admin/forum/topics")
    public ForumTopic createTopic(@RequestBody ForumTopicDTO dto) {
        return topicService.createTopic(dto);
    }
    @DeleteMapping("/admin/forum/topics/{id}")
    public void deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
    }
}