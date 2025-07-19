package com.example.backend_blood_donation_system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Inject giá trị từ application.properties vào biến uploadDir
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Sử dụng biến uploadDir đã được inject
        // Phải thêm "file:" để chỉ định rằng đây là một đường dẫn trên hệ thống file
        // Phải thêm dấu "/" ở cuối để nó là một thư mục
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}