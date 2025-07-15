package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.ForumTopicDTO;
import com.example.backend_blood_donation_system.entity.ForumTopic;
import com.example.backend_blood_donation_system.repository.ForumTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumTopicService {
    private final ForumTopicRepository topicRepo;

    public ForumTopic createTopic(ForumTopicDTO dto) {
        ForumTopic topic = new ForumTopic();
        topic.setTitle(dto.getTitle());
        topic.setDescription(dto.getDescription());
        topic.setCreatedBy(dto.getCreatedBy());
        return topicRepo.save(topic);
    }

    public List<ForumTopic> getAllTopics() {
        return topicRepo.findAll();
    }

    public void deleteTopic(Long id) {
        topicRepo.deleteById(id);
    }
}

