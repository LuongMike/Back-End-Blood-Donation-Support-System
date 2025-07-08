package com.example.backend_blood_donation_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("luongdvse181578@fpt.edu.vn"); // nên trùng với email trong cấu hình SMTP
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = nội dung là HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("❌ Lỗi gửi email đến: " + to);
            e.printStackTrace();
        }
    }
}
