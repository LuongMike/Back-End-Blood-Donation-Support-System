package com.example.backend_blood_donation_system.service;
import com.example.backend_blood_donation_system.dto.HealthCheckRequest;
import com.example.backend_blood_donation_system.entity.UserProfile;
import com.example.backend_blood_donation_system.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class HealthCheckService {
    @Autowired
    private UserProfileRepository repository;

    public UserProfile createHealthCheck(HealthCheckRequest request) {
        // Kiểm tra xem user đã có hồ sơ chưa
        if (repository.findByUserId(request.getUserId()).isPresent()) {
            throw new IllegalStateException("UserProfile already exists for userId: " + request.getUserId());
        }
        UserProfile profile = new UserProfile();
        profile.setUserId(request.getUserId());
        profile.setWeight(request.getWeight());
        profile.setBloodTypeId(request.getBloodTypeId());
        profile.setLastScreeningDate(request.getLastScreeningDate());
        profile.setHealthStatus(request.getHealthStatus());

        return repository.save(profile);
    }
    public UserProfile updateHealthCheck(Long userId, HealthCheckRequest request) {
        UserProfile profile = repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("UserProfile not found for userId: " + userId));

        profile.setWeight(request.getWeight());
        profile.setBloodTypeId(request.getBloodTypeId());
        profile.setLastScreeningDate(request.getLastScreeningDate());
        profile.setHealthStatus(request.getHealthStatus());

        return repository.save(profile);
    }
}
