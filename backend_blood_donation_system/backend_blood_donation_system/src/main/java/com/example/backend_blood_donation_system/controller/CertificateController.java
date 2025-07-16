package com.example.backend_blood_donation_system.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend_blood_donation_system.dto.CertificateDTO;
import com.example.backend_blood_donation_system.entity.DonationCertificate;
import com.example.backend_blood_donation_system.repository.DonationCertificateRepository;
import com.example.backend_blood_donation_system.security.CustomUserDetails;

@RestController
@RequestMapping("/api")
public class CertificateController {

    private final DonationCertificateRepository certificateRepository;

    public CertificateController(DonationCertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    // API cho người dùng lấy danh sách chứng nhận của mình
    @GetMapping("/user/certificates")
    @PreAuthorize("hasAuthority('MEMBER')")
    // Thay đổi kiểu trả về thành ResponseEntity<List<CertificateDTO>>
    public ResponseEntity<List<CertificateDTO>> getMyCertificates(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<DonationCertificate> certificates = certificateRepository.findByUser_UserIdOrderByIssueDateDesc(userDetails.getId());

        // Chuyển đổi từ List<DonationCertificate> sang List<CertificateDTO>
        List<CertificateDTO> certificateDTOs = certificates.stream()
            .map(cert -> new CertificateDTO(cert.getId(), cert.getCertificateCode(), cert.getIssueDate()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(certificateDTOs);
    }
        

    // API cho người dùng tải file PDF
    @GetMapping("/user/certificates/{id}/download")
    @PreAuthorize("hasAuthority('MEMBER')")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) throws MalformedURLException {
        DonationCertificate certificate = certificateRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy chứng nhận!"));

        // Đảm bảo người dùng chỉ có thể tải chứng nhận của chính họ
        if (!certificate.getUser().getUserId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).build();
        }

        Path filePath = Paths.get(certificate.getPdfStoragePath());
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
        } else {
            throw new RuntimeException("Không thể đọc file!");
        }
    }

    // API công khai để tra cứu tính hợp lệ của chứng nhận
    @GetMapping("/public/certificate/verify/{code}")
    public ResponseEntity<?> verifyCertificate(@PathVariable String code) {
        return certificateRepository.findByCertificateCode(code)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}