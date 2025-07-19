package com.example.backend_blood_donation_system.config;

import java.io.IOException;
import java.util.List;

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

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        // =================================================================
        // SỬA LỖI TẠI ĐÂY
        // Thêm tất cả các đường dẫn công khai vào danh sách cần bỏ qua.
        if (path.startsWith("/api/auth/") ||
            path.startsWith("/api/blog") ||       // Cho phép /api/blog và /api/blog/all
            path.startsWith("/uploads/") ||
            path.startsWith("/api/blood-types") ||
            path.startsWith("/api/component-types") ||
            path.startsWith("/api/public/") ||
            path.startsWith("/ws")) {
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
            Integer userId = jwtService.extractUserId(tokenValue);
            String username = jwtService.extractUsername(tokenValue);
            String role = jwtService.extractRole(tokenValue);

            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("User not found");
                return;
            }

            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            CustomUserDetails userDetails = new CustomUserDetails(user, userId, username, role, authorities);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                    authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Token is invalid or blacklisted");
        }
    }
}