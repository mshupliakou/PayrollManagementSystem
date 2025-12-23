package com.project_agh.payrollmanagementsystem.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDto {
    private Long id;
    private Long userId;
    private LocalDate date;
    private BigDecimal amount;
    private Long paymentId;
    private String description;
}
