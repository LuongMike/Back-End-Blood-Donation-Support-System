package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.ForumPostDTO;
import com.example.backend_blood_donation_system.entity.ForumPost;
import com.example.backend_blood_donation_system.entity.ForumTopic;
import com.example.backend_blood_donation_system.repository.ForumPostRepository;
import com.example.backend_blood_donation_system.repository.ForumTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumPostService {
    private final ForumPostRepository postRepo;
    private final ForumTopicRepository topicRepo;

    public ForumPost createPost(ForumPostDTO dto) {
        ForumTopic topic = topicRepo.findById(dto.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        ForumPost post = new ForumPost();
        post.setContent(dto.getContent());
        post.setTopic(topic);
        post.setAuthorId(dto.getAuthorId());
        post.setVisible(true);
        return postRepo.save(post);
    }

    public List<ForumPost> getVisiblePosts(Long topicId) {
        return postRepo.findByTopicIdAndVisibleTrue(topicId);
    }

    public void hidePost(Long postId) {
        ForumPost post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setVisible(false);
        postRepo.save(post);
    }
}

