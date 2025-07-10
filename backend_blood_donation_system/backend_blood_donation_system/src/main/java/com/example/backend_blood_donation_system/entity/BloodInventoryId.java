package com.example.backend_blood_donation_system.entity;

// vì có khóa chính phức hợp nên cần tạo class này

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
 // class này sẽ chứa các trường cần thiết để tạo khóa chính phức hợp => để tới bảng BloodInvetory
public class BloodInventoryId implements Serializable {
    
    private Integer centerId; 
    private Integer bloodTypeId; // ID của loại máu
    private Integer componentTypeId; // ID của thành phần máu

    
    public BloodInventoryId() {
    }
    
    public BloodInventoryId(Integer centerId,Integer bloodTypeId, Integer componentTypeId) {
        this.centerId = centerId; // ID của trung tâm máu
        this.bloodTypeId = bloodTypeId;
        this.componentTypeId = componentTypeId;
    }

     // Bắt buộc phải override equals() và hashCode()
     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         BloodInventoryId that = (BloodInventoryId) o;
         return Objects.equals(centerId, that.centerId) &&
                Objects.equals(componentTypeId, that.componentTypeId) &&
                Objects.equals(bloodTypeId, that.bloodTypeId);
     }
 
     @Override
     public int hashCode() {
         return Objects.hash(centerId, componentTypeId, bloodTypeId);
     }
     
}
