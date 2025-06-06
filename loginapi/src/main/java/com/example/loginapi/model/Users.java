package com.example.loginapi.model;
import jakarta.persistence.*;
@Entity
@Table(name="User")

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String user_id;

    private String username;
    private String password;
    private String gender;
    private String email;
    private String full_name;
    private String phone_number;
    private String role;
    private String address;
    private String created_at;

    public Users() {
    }

    public Users(String created_at, String address, String role, String phone_number, String full_name, String mail, String gender, String password, String username, String user_id) {
        this.created_at = created_at;
        this.address = address;
        this.role = role;
        this.phone_number = phone_number;
        this.full_name = full_name;
        this.email = mail;
        this.gender = gender;
        this.password = password;
        this.username = username;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String mail) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}