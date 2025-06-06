package com.example.backend_blood_donation_system.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend_blood_donation_system.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        // Bỏ qua filter cho các endpoint public
        if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register")) {
            filterChain.doFilter(request, response);
        return;
        }
        
        String token = request.getHeader("Authorization");
        String tokenValue = null;

        if (token != null && token.startsWith("Bearer ")) {
            tokenValue = token.substring(7);
        }

        if (tokenValue != null && !jwtService.isTokenBlacklisted(tokenValue) && !jwtService.isTokenExpired(tokenValue)) {
            // Token hợp lệ, tiếp tục filter chain
            filterChain.doFilter(request, response);
        } else {
            // Token không hợp lệ hoặc bị blacklist
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token is invalid or blacklisted");
        }
    }
}