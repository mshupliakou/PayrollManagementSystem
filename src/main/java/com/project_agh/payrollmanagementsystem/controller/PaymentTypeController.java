package com.project_agh.payrollmanagementsystem.controller;


import com.project_agh.payrollmanagementsystem.dtos.PaymentTypeDto;
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
@RequestMapping("accountant/payments/types")
@PreAuthorize("hasRole('ROLE_ACCOUNTANT')")
public class PaymentTypeController {

    private final PaymentTypeRepository paymentTypeRepository;
    public PaymentTypeController(PaymentTypeRepository paymentTypeRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
    }

    @PostMapping("/create")
    public String createPaymentType(
            @ModelAttribute PaymentTypeDto paymetTypeDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            paymentTypeRepository.createPaymentType(
                    paymetTypeDto.getName(),
                    paymetTypeDto.getDescription()
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



        return "redirect:/dashboard?tab=types";
    }


    @PostMapping("/delete")
    public String deletePaymentType(
            @ModelAttribute PaymentTypeDto paymetTypeDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            paymentTypeRepository.deletePaymentType(
                    paymetTypeDto.getId()
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



        return "redirect:/dashboard?tab=types";
    }

    @PostMapping("/edit")
    public String editPaymentType(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            RedirectAttributes redirectAttributes) {

        try {

            paymentTypeRepository.editPaymentType(
                    id, name,  description
            );

            redirectAttributes.addFlashAttribute("successMessage",
                    "Dane typu (ID: " + id + ") zostały zaktualizowane.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error editing role: " + e.getMessage());
        }

        return "redirect:/dashboard?tab=types";
    }
}