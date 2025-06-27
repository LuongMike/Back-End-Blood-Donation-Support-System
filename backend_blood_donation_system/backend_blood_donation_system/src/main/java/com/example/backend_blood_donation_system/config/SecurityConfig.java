package com.example.backend_blood_donation_system.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                // Kích hoạt CORS và sử dụng Bean 'corsConfigurationSource' bên dưới
                .cors(withDefaults())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/auth/**").permitAll() // Cho phép lấy danh sách trung tâm mà không cần login
                        .requestMatchers(
                                "/api/DonationCenter", // public API
                                "/ws/**" // cho phép kết nối WebSocket endpoint
                        ).permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                        // THÊM DÒNG NÀY: Cho phép user có quyền STAFF truy cập vào /api/staff/**
                        .requestMatchers("/api/staff/**").hasAuthority("STAFF")
                        
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean này sẽ định nghĩa các quy tắc CORS cho Spring Security
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        //configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Cho phép frontend
        configuration.setAllowedOrigins(List.of("http://localhost:5177"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // Cho phép mọi header
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng cho mọi đường dẫn

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}