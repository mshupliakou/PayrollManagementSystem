package com.project_agh.payrollmanagementsystem.controller;


import com.project_agh.payrollmanagementsystem.dtos.PaymentStatusDto;
import com.project_agh.payrollmanagementsystem.dtos.PaymentTypeDto;
import com.project_agh.payrollmanagementsystem.repositories.PaymentStatusRepository;
import com.project_agh.payrollmanagementsystem.repositories.PaymentTypeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("accountant/payments/statuses")
@PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
public class PaymentStatusController {

    private final PaymentStatusRepository paymentStatusRepository;

    public PaymentStatusController(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    @PostMapping("/create")
    public String createPaymentStatus(
            @ModelAttribute PaymentStatusDto paymentStatusDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            paymentStatusRepository.createPaymentStatus(
                    paymentStatusDto.getName(),
                    paymentStatusDto.getDescription()
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Payment Type has been created successfully."
            );

        } catch (Exception e) {
            System.err.println("Error creating role: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to create role. Details: " + e.getMessage()
            );
        }



        return "redirect:/dashboard?tab=statuses";
    }


    @PostMapping("/delete")
    public String deletePaymentStatus(
            @ModelAttribute PaymentStatusDto paymentStatusDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            paymentStatusRepository.deletePaymentStatus(
                    paymentStatusDto.getId()
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "PaymentType has been deleted successfully."
            );

        } catch (Exception e) {
            System.err.println("Error deleting paymnet type: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to delete type. Details: " + e.getMessage()
            );
        }



        return "redirect:/dashboard?tab=statuses";
    }

    @PostMapping("/edit")
    public String editPaymentStatus(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            RedirectAttributes redirectAttributes) {

        try {

            paymentStatusRepository.editPaymentStatus(
                    id, name,  description
            );

            redirectAttributes.addFlashAttribute("successMessage",
                    "Dane typu (ID: " + id + ") zostały zaktualizowane.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error editing role: " + e.getMessage());
        }

        return "redirect:/dashboard?tab=statuses";
    }
}