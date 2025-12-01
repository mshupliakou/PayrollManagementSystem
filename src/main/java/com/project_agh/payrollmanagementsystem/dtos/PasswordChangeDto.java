package com.project_agh.payrollmanagementsystem.dtos;

import lombok.Data;

@Data
public class PasswordChangeDto {
        private String currentPassword;
        private String newPassword;
        private String confirmNewPassword;
}
