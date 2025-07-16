package com.example.backend_blood_donation_system.service;

import com.example.backend_blood_donation_system.dto.ForumTopicDTO;
import com.example.backend_blood_donation_system.entity.ForumTopic;
import com.example.backend_blood_donation_system.repository.ForumTopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<ForumTopicDTO> getAllTopics() {
        return topicRepo.findAll().stream()
                .map(this::convertToTopicDto)
                .collect(Collectors.toList());
    }

    public void deleteTopic(Long id) {
        topicRepo.deleteById(id);
    }

        private ForumTopicDTO convertToTopicDto(ForumTopic topic) {
        return new ForumTopicDTO(
            topic.getId(),
            topic.getTitle(),
            topic.getDescription(),
            topic.getCreatedBy(),
            topic.getCreatedAt(),
            topic.getPosts() != null ? topic.getPosts().size() : 0 
        );
    }
}

