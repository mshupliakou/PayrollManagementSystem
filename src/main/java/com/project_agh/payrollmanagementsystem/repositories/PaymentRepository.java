package com.project_agh.payrollmanagementsystem.repositories;

import com.project_agh.payrollmanagementsystem.entities.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository {
    List<Payment> findAll();

    void createPayment();
    void deletePayment(Long id);
    void editPayment();
    void updateStatus(Long id, Long statusId);
}
