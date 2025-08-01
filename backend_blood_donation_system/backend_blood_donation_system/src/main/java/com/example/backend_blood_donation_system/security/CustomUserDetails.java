package com.example.backend_blood_donation_system.security;

import java.util.Collection;

import com.example.backend_blood_donation_system.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String role;
    private final Collection<? extends GrantedAuthority> authorities;
    private final User user;


    public CustomUserDetails(User user,Integer id, String username, String role,
                             Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.id = id;
        this.username = username;
        this.role = role;
        this.authorities = authorities;
    }

    public User getUser() {
        return this.user;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // ✅ đã chứa ROLE_ prefix
    }

    @Override
    public String getPassword() {
        return null; // vì dùng JWT nên không cần
    }

    @Override
    public String getUsername() {
        return username;
    }

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
