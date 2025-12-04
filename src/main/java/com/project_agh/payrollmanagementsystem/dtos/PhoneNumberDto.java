package com.project_agh.payrollmanagementsystem.dtos;

import lombok.Data;

/**
 * DTO used to capture user input when changing the phone number.
 * <p>
 * Contains a single field representing the updated phone number
 * that should be saved for the authenticated user.
 * </p>
 */
@Data
public class PhoneNumberDto {

    /**
     * The new phone number provided by the user.
     */
    private String phoneNumber;
}
