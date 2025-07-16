package com.example.backend_blood_donation_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.ForumPostDTO;
import com.example.backend_blood_donation_system.dto.ForumTopicDTO;
import com.example.backend_blood_donation_system.entity.ForumPost;
import com.example.backend_blood_donation_system.entity.ForumTopic;
import com.example.backend_blood_donation_system.service.ForumPostService;
import com.example.backend_blood_donation_system.service.ForumTopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ForumController {

    private final ForumTopicService topicService;
    private final ForumPostService postService;

    // ========== PUBLIC ==========
    @GetMapping("/forum/topics")
    public List<ForumTopicDTO> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/forum/topics/{id}/posts")
    public List<ForumPostDTO> getVisiblePosts(@PathVariable Long id) {
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

    @PutMapping("/admin/forum/posts/{id}/hide")
    public void hidePost(@PathVariable Long id) {
        postService.hidePost(id);
    }
}