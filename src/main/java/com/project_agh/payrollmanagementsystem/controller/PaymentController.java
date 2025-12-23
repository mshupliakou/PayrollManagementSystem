package com.project_agh.payrollmanagementsystem.controller;

import com.project_agh.payrollmanagementsystem.dtos.PaymentDto;
import com.project_agh.payrollmanagementsystem.dtos.PaymentStatusDto;
import com.project_agh.payrollmanagementsystem.repositories.PaymentRepository;
import com.project_agh.payrollmanagementsystem.repositories.PaymentStatusRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("accountant/payments")
@PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping("/bulk-delete")
    public String deletePayment(
            @ModelAttribute PaymentDto paymentDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            paymentRepository.deletePayment(paymentDto.getPaymentId());

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Payment has been deleted successfully."
            );

        } catch (Exception e) {
            System.err.println("Error deleting paymnet : " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to delete payment. Details: " + e.getMessage()
            );
        }



        return "redirect:/dashboard?tab=paymnets";
    }
}
