package com.example.backend_blood_donation_system.config;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.example.backend_blood_donation_system.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    public WebSocketHandshakeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
            org.springframework.http.server.ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        System.out.println("Interceptor beforeHandshake đang chạy");
        System.out.println("👉 Interceptor STARTED");
        if (request instanceof ServletServerHttpRequest servletRequest) {
            System.out.println("✅ Đã vào instanceof ServletServerHttpRequest");
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            String token = httpRequest.getParameter("token");

            if (token != null) {
                try {
                    if (jwtService.isTokenExpired(token) || jwtService.isTokenBlacklisted(token)) {
                        System.out.println("❌ Token hết hạn hoặc nằm trong blacklist");
                        return false;
                    }

                    String username = jwtService.extractUsername(token);
                    Integer userId = jwtService.extractUserId(token);
                    String role = jwtService.extractRole(token);

                    System.out.println("✅ WebSocket Handshake - Authenticated user: " + username + ", Role: " + role);

                    Principal principal = new UsernamePasswordAuthenticationToken(
                            username, null, Collections.emptyList());

                    // Lưu Principal vào attributes để các phần khác có thể truy cập
                    attributes.put("principal", principal);
                    httpRequest.setAttribute("principal", principal); 
                } catch (Exception e) {
                    System.out.println("❌ Token không hợp lệ: " + e.getMessage());
                    return false;
                }
            } else {
                System.out.println("❌ Không có token trong query ?token=");
                return false;
            }
        }

        return true; // cho phép handshake tiếp tục nếu không có lỗi
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
            org.springframework.http.server.ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception) {
        // Không cần xử lý gì sau khi bắt tay
    }
}
