package com.project_agh.payrollmanagementsystem.dtos;

import lombok.Data;

/**
 * DTO used for handling password change form submissions.
 * <p>
 * Encapsulates the current password for verification, and the new password
 * along with its confirmation to ensure they match before updating.
 * </p>
 */
@Data
public class PasswordChangeDto {

    /**
     * The current password of the user (unencrypted), used to verify identity.
     */
    private String currentPassword;

    /**
     * The new password the user wants to set (unencrypted).
     */
    private String newPassword;

    /**
     * Confirmation of the new password to avoid typos or mismatches.
     */
    private String confirmNewPassword;
}
