package com.example.backend_blood_donation_system.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value; // Thêm import này
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Thêm import này
import org.springframework.web.multipart.MultipartFile;

import com.example.backend_blood_donation_system.dto.BlogPostResponse;
import com.example.backend_blood_donation_system.entity.BlogPost;
import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.enums.PostType;
import com.example.backend_blood_donation_system.repository.BlogPostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Thêm import này để log lỗi (khuyến khích)

@Service
@RequiredArgsConstructor
@Slf4j // Thêm annotation này để có thể dùng log
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    // Inject giá trị từ application.properties vào biến uploadDir
    @Value("${file.upload-dir}")
    private String uploadDirString;

    @Transactional // Đảm bảo tất cả các thao tác (xóa file, xóa db) hoặc thành công hoặc thất bại cùng nhau
    public void createPost(String title, String content, String type, MultipartFile image, User author) {
        BlogPost post = new BlogPost();
        post.setAuthor(author);
        post.setTitle(title);
        post.setContent(content);
        post.setType(PostType.valueOf(type.toUpperCase())); // "BLOG", "NEWS"
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        // Nếu có file ảnh thì lưu
        if (image != null && !image.isEmpty()) {
            try {
                // Tạo thư mục nếu chưa tồn tại
                Path uploadDir = Paths.get(uploadDirString);
                Files.createDirectories(uploadDir);
                
                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Path filePath = uploadDir.resolve(filename);
                Files.write(filePath, image.getBytes());

                // Lưu đường dẫn web, không phải đường dẫn hệ thống
                post.setImage("/api/uploads/" + filename); 
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi lưu ảnh", e);
            }
        }

        blogPostRepository.save(post);
    }

    public List<BlogPostResponse> getAllPosts() {
        List<BlogPost> posts = blogPostRepository.findAll();

        return posts.stream().map(this::mapToBlogPostResponse).collect(Collectors.toList());
    }

    @Transactional // Đảm bảo tất cả các thao tác (xóa file, xóa db) hoặc thành công hoặc thất bại cùng nhau
    public void deletePost(Integer postId) {
        // 1. Tìm bài viết trong DB, nếu không có thì báo lỗi
        BlogPost post = blogPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Bài viết không tồn tại với ID: " + postId));

        // 2. Lấy đường dẫn ảnh từ bài viết
        String imagePath = post.getImage();

        // 3. Kiểm tra nếu có ảnh thì tiến hành xóa file
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Lấy tên file từ đường dẫn (ví dụ: từ "/uploads/abc.jpg" -> "abc.jpg")
                String filename = imagePath.substring(imagePath.lastIndexOf("/") + 1);
                
                // Tạo đường dẫn đầy đủ đến file trên server
                Path filePath = Paths.get(uploadDirString).resolve(filename);

                // Xóa file nếu nó tồn tại
                Files.deleteIfExists(filePath);
                log.info("Đã xóa file ảnh thành công: " + filePath);

            } catch (IOException e) {
                // Ghi log lỗi thay vì crash ứng dụng
                log.error("Lỗi khi xóa file ảnh của bài viết ID " + postId, e);
                // Bạn có thể quyết định ném ra một exception ở đây nếu việc xóa file là bắt buộc
                // throw new RuntimeException("Không thể xóa file ảnh, thao tác bị hủy.", e);
            }
        }

        // 4. Sau khi đã xử lý file, tiến hành xóa bài viết khỏi DB
        blogPostRepository.delete(post);
    }

    public BlogPostResponse getPostById(Integer postId) {
        BlogPost post = blogPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với ID: " + postId));
        return mapToBlogPostResponse(post);
    }

    // Helper method để tránh lặp code
    private BlogPostResponse mapToBlogPostResponse(BlogPost post) {
        BlogPostResponse dto = new BlogPostResponse();
        dto.setPostId(post.getPostId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setImage(post.getImage());
        dto.setType(post.getType());
        dto.setAuthorName(post.getAuthor().getFullName());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }
}