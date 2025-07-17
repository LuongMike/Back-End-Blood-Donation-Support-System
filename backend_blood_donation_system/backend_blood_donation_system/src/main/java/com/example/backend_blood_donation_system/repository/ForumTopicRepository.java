package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.ForumTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumTopicRepository extends JpaRepository<ForumTopic, Long> {
}
