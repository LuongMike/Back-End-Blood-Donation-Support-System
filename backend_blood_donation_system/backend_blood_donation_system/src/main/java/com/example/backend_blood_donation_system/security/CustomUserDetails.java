package com.example.backend_blood_donation_system.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String role;
    private final Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(Integer id, String username, String role, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.authorities = authorities;
    }


    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // Không cần password vì đã xác thực bằng JWT
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Các method còn lại trả về mặc định vì không dùng đến

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
