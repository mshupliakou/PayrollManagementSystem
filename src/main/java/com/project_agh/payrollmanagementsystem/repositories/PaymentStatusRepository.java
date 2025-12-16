package com.project_agh.payrollmanagementsystem.repositories;

import com.project_agh.payrollmanagementsystem.entities.PaymentStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentStatusRepository {
    List<PaymentStatus> findAll();

    void createPaymentStatus(String name, String description );

    void deletePaymentStatus(Long id);

    void editPaymentStatus(Long id, String name, String description);
}
