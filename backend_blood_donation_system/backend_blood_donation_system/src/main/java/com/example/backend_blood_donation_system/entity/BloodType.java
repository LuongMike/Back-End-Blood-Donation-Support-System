package com.example.backend_blood_donation_system.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BloodType")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blood_type_id")
    private Integer id;

    @Column(name = "type", nullable = false, unique = true)
    @NotBlank(message = "Blood type cannot be blank")
    @Size(max = 3, message = "Blood type cannot exceed 3 characters")

    private String type;
//=======
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "blood_type")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class BloodType {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hibernate dùng IDENTITY
//    private Long blood_type_id;
//
//    private String type;
//
//
//>>>>>>> Feature-View-User
}
