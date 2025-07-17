package com.example.backend_blood_donation_system.repository;

import com.example.backend_blood_donation_system.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
}
