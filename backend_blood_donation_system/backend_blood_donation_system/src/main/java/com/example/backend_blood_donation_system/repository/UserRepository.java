package com.example.backend_blood_donation_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend_blood_donation_system.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Tìm user theo username hoặc email
    @Query("SELECT u FROM User u WHERE u.username = :login OR u.email = :login")
    Optional<User> findByUsernameOrEmail(@Param("login") String login);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // Tìm user theo ID
    Optional<User> findById(Integer id);

    List<User> findByRole(String role);

    List<User> findAllByRole(String role);

    @Query("SELECT u FROM User u " +
       "LEFT JOIN FETCH u.profile p " +
       "LEFT JOIN FETCH p.bloodType " +
       "WHERE u.role = 'MEMBER' ")
List<User> findAllMembersWithProfileAndBloodType();
}