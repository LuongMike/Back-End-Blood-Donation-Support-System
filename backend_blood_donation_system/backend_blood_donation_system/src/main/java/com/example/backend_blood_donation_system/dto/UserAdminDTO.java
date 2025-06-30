package com.example.backend_blood_donation_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserAdminDTO {

    @NotBlank(message = "Tên tài khoản không được để trống")
    @Size(min = 3, max = 50, message = "Tên tài khoản phải có độ dài từ 3 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tên tài khoản chỉ được chứa chữ cái, số và dấu gạch dưới")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 100, message = "Mật khẩu phải có độ dài từ 6 đến 100 ký tự")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,100}$",
            message = "Mật khẩu phải chứa ít nhất một chữ cái viết hoa, một chữ cái viết thường, một số và một ký tự đặc biệt")
    private String password;

    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 3, max = 50, message = "Họ và tên phải có độ dài từ 3 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Họ và tên chỉ được chứa chữ cái và khoảng trắng")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Số điện thoại không hợp lệ")
    @Size(min = 10, max = 15, message = "Số điện thoại phải có độ dài từ 10 đến 15 ký tự")
    private String phoneNumber;

    @NotBlank(message = "Giới tính không được để trống")
    @Pattern(regexp = "^(Nam|Nữ|Khác)$", message = "Giới tính phải là 'Nam', 'Nữ' hoặc 'Khác'")
    private String gender;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    @NotBlank(message = "Vai trò không được để trống")
    @Pattern(regexp = "^(ADMIN|MEMBER|STAFF)$", message = "Vai trò phải là 'Admin' hoặc 'User' hoặc 'Staff'")
    private String role;
}
