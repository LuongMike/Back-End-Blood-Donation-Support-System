package com.example.backend_blood_donation_system.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend_blood_donation_system.entity.User;
import com.example.backend_blood_donation_system.repository.UserRepository;
import com.example.backend_blood_donation_system.security.CustomUserDetails;
import com.example.backend_blood_donation_system.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Dòng log để kiểm tra
        logger.info("Incoming request to path: {}", path);

        // =================================================================
        // LOGIC QUAN TRỌNG NHẤT
        // Nếu request đi đến một trong các đường dẫn công khai, filter sẽ cho qua luôn.
        if (isPublicPath(path)) {
            logger.info("Path is public, skipping JWT check.");
            filterChain.doFilter(request, response);
            return;
        }
        // =================================================================

        String token = request.getHeader("Authorization");
        String tokenValue = null;

        if (token != null && token.startsWith("Bearer ")) {
            tokenValue = token.substring(7);
        }

        if (tokenValue != null && !jwtService.isTokenBlacklisted(tokenValue)
                && !jwtService.isTokenExpired(tokenValue)) {
            // ... (Phần code xác thực token giữ nguyên)
            Integer userId = jwtService.extractUserId(tokenValue);
            String username = jwtService.extractUsername(tokenValue);
            String role = jwtService.extractRole(tokenValue);
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            CustomUserDetails userDetails = new CustomUserDetails(user, userId, username, role, authorities);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } else {
            logger.warn("JWT Token is missing, invalid, or blacklisted for path: {}", path);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Token is invalid or blacklisted");
        }
    }

    // Hàm helper để kiểm tra đường dẫn công khai
    private boolean isPublicPath(String path) {
        return path.startsWith("/api/auth/") ||
                path.startsWith("/api/blog") ||
                path.startsWith("/api/uploads/") ||
                path.startsWith("/uploads/") || // Thêm cả đường dẫn này cho chắc chắn
                path.startsWith("/api/blood-types") ||
                path.startsWith("/api/component-types") ||
                path.startsWith("/api/public/") ||
                path.startsWith("/ws");
    }
}