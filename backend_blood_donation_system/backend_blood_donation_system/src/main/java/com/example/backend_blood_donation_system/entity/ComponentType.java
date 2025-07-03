// File: ComponentType.java

package com.example.backend_blood_donation_system.entity;

import org.hibernate.annotations.Nationalized; // <<<<< IMPORT THÊM DÒNG NÀY

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
@Table(name = "ComponentType")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_type_id")
    private Integer id;

    // === SỬA ĐỔI TẠI ĐÂY ===
    @Nationalized // Dùng annotation này thay cho columnDefinition
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Component type name cannot be blank")
    @Size(max = 100, message = "Component type name cannot exceed 100 characters")
    private String name;

    // === SỬA ĐỔI TẠI ĐÂY ===
    @Nationalized // Dùng annotation này thay cho columnDefinition
    @Column(name = "description")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}