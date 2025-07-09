package com.example.backend_blood_donation_system.service;


import com.example.backend_blood_donation_system.dto.NotificationRequest;
import com.example.backend_blood_donation_system.dto.NotificationResponse;
import com.example.backend_blood_donation_system.entity.*;
import com.example.backend_blood_donation_system.enums.NotificationType;
import com.example.backend_blood_donation_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final UserNotificationRepository userNotificationRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final NotificationDonationResponseRepository donationResponseRepository;
    @Autowired
    private final DonationHistoryRepository donationHistoryRepository;
    public void createNotification(NotificationRequest request, Integer staffId) {
        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setCreatedBy(staff);
        notification.setType(request.getType());

        notificationRepository.save(notification);

        List<User> members = userRepository.findAllByRole("MEMBER");

//        List<UserNotification> userNotifications = members.stream().map(user -> {
//            UserNotification un = new UserNotification();
//            un.setUser(user);
//            un.setNotification(notification);
//            return un;
//        }).collect(Collectors.toList());
//
//        userNotificationRepository.saveAll(userNotifications);
        List<UserNotification> userNotifications = members.stream()
                .filter(user -> {
                    if (request.getType() == NotificationType.DONATION_REQUEST) {
                        return isEligibleToReceiveNotification(user); // lọc nếu là yêu cầu hiến máu
                    }
                    return true; // với GENERAL_INFO thì gửi tất
                })
                .map(user -> {
                    UserNotification un = new UserNotification();
                    un.setUser(user);
                    un.setNotification(notification);
                    return un;
                })
                .collect(Collectors.toList());

        userNotificationRepository.saveAll(userNotifications);
    }

    public List<NotificationResponse> getUserNotifications(Integer userId) {
        List<UserNotification> rawList = userNotificationRepository.findByUser_UserId(userId);

        return rawList.stream().map(un -> {
            Notification n = un.getNotification();
            NotificationResponse dto = new NotificationResponse();
            dto.setNotificationId(n.getNotificationId());
            dto.setTitle(n.getTitle());
            dto.setMessage(n.getMessage());
            dto.setCreatedAt(n.getCreatedAt());
            dto.setRead(un.isRead());
            dto.setType(n.getType());
            dto.setNotificationId(n.getNotificationId());
            return dto;
        }).collect(Collectors.toList());
    }

    public void sendToAllDonors(NotificationRequest request, Integer staffId) {
        // ✅ Tìm staff tạo thông báo
        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        // ✅ Tạo Notification mới (1 bản ghi duy nhất)
        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setCreatedBy(staff);
        notification.setCreatedAt(LocalDateTime.now());

        // ✅ Lưu thông báo
        notificationRepository.save(notification);
    }

    public void acceptDonationRequest(Integer userId, Integer notificationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // Kiểm tra nếu đã đồng ý rồi
        if (donationResponseRepository.existsByUserAndNotification(user, notification)) {
            throw new RuntimeException("Bạn đã đồng ý hiến máu thông báo này trước đó.");
        }

        NotificationDonationResponse response = new NotificationDonationResponse();
        response.setUser(user);
        response.setNotification(notification);
        donationResponseRepository.save(response);
    }

    // ✅ Kiểm tra điều kiện đủ để hiến máu
    private boolean isEligibleToReceiveNotification(User user) {
        Optional<DonationHistory> latestOpt = donationHistoryRepository.findTopByUserOrderByDonationDateDesc(user);
        if (latestOpt.isEmpty()) return true;

        DonationHistory latest = latestOpt.get();
        if (latest.getComponentType() == null) return true;

        String component = latest.getComponentType().getName();
        int weeks = getRecoveryWeeks(component);
        LocalDate eligibleDate = latest.getDonationDate().plusWeeks(weeks);

        return LocalDate.now().isAfter(eligibleDate);
    }
    // ✅ Tính số tuần phục hồi dựa trên loại máu
    private int getRecoveryWeeks(String componentTypeName) {
        if (componentTypeName == null) return 0;
        return switch (componentTypeName.trim().toLowerCase()) {
            case "plasma", "platelets" -> 2;
            case "red blood cells", "white blood cells" -> 16;
            default -> 0;
        };
    }

}
