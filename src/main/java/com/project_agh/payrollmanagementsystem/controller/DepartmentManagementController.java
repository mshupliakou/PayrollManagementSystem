package com.project_agh.payrollmanagementsystem.controller;

import com.project_agh.payrollmanagementsystem.dtos.CreateDepartmentDto;
import com.project_agh.payrollmanagementsystem.dtos.RoleDto;
import com.project_agh.payrollmanagementsystem.repositories.DepartmentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Controller responsible for managing department-related administrative operations.
 * <p>
 * This controller provides functionality available only to users with the {@code ROLE_ADMIN} role.
 * It handles department creation via a POST request and delegates persistence logic to the
 * {@link DepartmentRepository}.
 */
@Controller
@RequestMapping("admin/departments")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DepartmentManagementController {

    /**
     * Repository used for executing department-related database operations.
     */
    private final DepartmentRepository departmentRepository;

    /**
     * Constructs a new {@code DepartmentManagementController} with the required dependencies.
     *
     * @param departmentRepository the repository used for department persistence operations
     */
    public DepartmentManagementController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Handles the creation of a new department.
     * <p>
     * The method receives department data through a {@link CreateDepartmentDto} object,
     * invokes the repository layer to persist the department, and then redirects the user
     * back to the dashboard. Success or error messages are passed using flash attributes.
     *
     * @param newDepartmentForm   the DTO containing submitted department details
     * @param redirectAttributes  used to supply success or error messages after redirect
     * @param request             the current HTTP request (automatically injected by Spring)
     * @return redirect instruction to the dashboard view
     */
    @PostMapping("/create")
    public String createDepartment(
            @ModelAttribute CreateDepartmentDto newDepartmentForm,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            departmentRepository.createDepartment(
                    newDepartmentForm.getDepartment_name(),
                    newDepartmentForm.getDepartment_desc()
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Department has been created successfully."
            );

        } catch (Exception e) {
            System.err.println("Error creating department: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to create department. Details: " + e.getMessage()
            );
        }

        return "redirect:/dashboard?tab=departments";
    }

    @PostMapping("/delete")
    public String deleteDepartment(
            @ModelAttribute CreateDepartmentDto departmentDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            departmentRepository.deleteDepartment(
                    departmentDto.getId()
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Department has been deleted successfully."
            );

        } catch (Exception e) {
            System.err.println("Error deleting role: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to delete role. Details: " + e.getMessage()
            );
        }



        return "redirect:/dashboard?tab=departments";
    }

    @PostMapping("/edit")
    public String editDepartment(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            RedirectAttributes redirectAttributes) {

        try {

                departmentRepository.editDepartment(
                        id, name, description
                );

            redirectAttributes.addFlashAttribute("successMessage",
                    "Dane działa (ID: " + id + ") zostały zaktualizowane.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error editing department: " + e.getMessage());
        }

        return "redirect:/dashboard?tab=departments";
    }
}
