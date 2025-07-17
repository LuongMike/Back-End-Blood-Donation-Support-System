package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {
    List<ForumPost> findByTopicIdAndVisibleTrue(Long topicId);
}
