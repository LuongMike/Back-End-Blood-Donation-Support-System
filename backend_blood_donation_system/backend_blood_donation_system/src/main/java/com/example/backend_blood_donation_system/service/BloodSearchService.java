package com.example.backend_blood_donation_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend_blood_donation_system.dto.NearbyDonorDTO;
import com.example.backend_blood_donation_system.entity.DonationHistory;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.DonationHistoryRepository;
import com.example.backend_blood_donation_system.repository.UserRepository;
import com.example.backend_blood_donation_system.utils.DistanceUtil;

@Service
public class BloodSearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private EmailService emailService;

    public List<NearbyDonorDTO> findNearbyDonors(double centerLat, double centerLng, double maxDistanceKm,
            String bloodType) {
        return userRepository.findAllByRole("MEMBER").stream()
                .filter(u -> u.getLatitude() != null && u.getLongitude() != null)
                .filter(u -> DistanceUtil.calculateDistance(centerLat, centerLng, u.getLatitude(),
                        u.getLongitude()) <= maxDistanceKm)
                .map(user -> {
                    Optional<DonationHistory> latestDonationOpt = donationHistoryRepository
                            .findTopByUserOrderByDonationDateDesc(user);

                    if (latestDonationOpt.isEmpty())
                        return null;
                    DonationHistory latest = latestDonationOpt.get();

                    if (bloodType != null && !bloodType.isBlank()) {
                        if (latest.getBloodType() == null
                                || !latest.getBloodType().getType().equalsIgnoreCase(bloodType.trim())) {
                            return null;
                        }
                    }

                    boolean eligible = true;
                    if (latest.getComponentType() != null) {
                        String component = latest.getComponentType().getName();
                        int weeks = getRecoveryWeeks(component);
                        LocalDate eligibleDate = latest.getDonationDate().plusWeeks(weeks);
                        eligible = LocalDate.now().isAfter(eligibleDate);
                    }

                    NearbyDonorDTO dto = new NearbyDonorDTO();
                    dto.setUserId(user.getUserId());
                    dto.setFullName(user.getFullName());
                    dto.setEmail(user.getEmail());
                    dto.setAddress(user.getAddress());
                    dto.setLatitude(user.getLatitude());
                    dto.setLongitude(user.getLongitude());
                    dto.setBloodType(latest.getBloodType() != null ? latest.getBloodType().getType() : null);
                    dto.setEligible(eligible);
                    return dto;
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());
    }

    public void sendEmailToUsers(List<Integer> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        String subject = "❤️ Lời kêu gọi hiến máu từ Trung tâm";

        String htmlBody = """
                <html>
                <body style=\"font-family: Arial, sans-serif; padding: 20px; background-color: #f9fafb;\">
                    <h2 style=\"color: #d32f2f;\">🩸 Lời kêu gọi hiến máu</h2>
                    <p>Chào bạn,</p>
                    <p>Trung tâm hiến máu đang rất cần <strong>máu phù hợp với nhóm máu của bạn</strong>.</p>
                    <p>Bạn đang ở gần trung tâm, và đây là cơ hội để bạn cứu giúc những người đang cần máu khẩn cấp.</p>
                    <p>Nếu bạn sẵn sàng hiến máu, vui lòng nhấn vào nút bên dưới để truy cập hệ thống:</p>
                    <p style=\"text-align: center;\">
                        <a href=\"http://localhost:3000\" style=\"display: inline-block; padding: 12px 24px; background-color: #ef4444; color: white; text-decoration: none; border-radius: 6px;\">
                            Xác nhận hiến máu
                        </a>
                    </p>
                    <p>Xin chân thành cảm ơn bạn vì nghĩa cử cao đẹp!</p>
                    <br/>
                    <p style=\"font-size: 13px; color: #6b7280;\">Trung tâm hiến máu | FPT TP.HCM</p>
                </body>
                </html>
                """;

        for (User user : users) {
            Optional<DonationHistory> latestDonation = donationHistoryRepository
                    .findTopByUserOrderByDonationDateDesc(user);
            if (latestDonation.isEmpty() || user.getEmail() == null)
                continue;

            DonationHistory history = latestDonation.get();
            boolean eligible = true;
            if (history.getComponentType() != null) {
                String type = history.getComponentType().getName();
                int weeks = getRecoveryWeeks(type);
                LocalDate eligibleDate = history.getDonationDate().plusWeeks(weeks);
                eligible = LocalDate.now().isAfter(eligibleDate);
            }

            if (eligible) {
                emailService.sendHtmlEmail(user.getEmail(), subject, htmlBody);
            }
        }
    }

    private int getRecoveryWeeks(String componentTypeName) {
        if (componentTypeName == null)
            return 0;
        return switch (componentTypeName.trim().toLowerCase()) {
            case "plasma", "platelets" -> 2;
            case "red blood cells", "white blood cells" -> 16;
            default -> 0;
        };
    }
}
