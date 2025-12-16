package com.project_agh.payrollmanagementsystem.controller;

import com.project_agh.payrollmanagementsystem.dtos.ProjectDto;
import com.project_agh.payrollmanagementsystem.dtos.WorkTypeDto;
import com.project_agh.payrollmanagementsystem.repositories.ProjectRepository;
import com.project_agh.payrollmanagementsystem.repositories.WorkTypeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/work_types")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class WorkTypeController {

    private final WorkTypeRepository workTypeRepository;

    public WorkTypeController(WorkTypeRepository workTypeRepository) {

        this.workTypeRepository = workTypeRepository;
    }

    @PostMapping("/create")
    public String createWorkType(
            @ModelAttribute WorkTypeDto workTypeDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            workTypeRepository.createWorkType(
                    workTypeDto.getName(),
                    workTypeDto.getDescription()
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "WorkType has been created successfully."
            );

        } catch (Exception e) {
            System.err.println("Error creating work type: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to create work type. Details: " + e.getMessage()
            );
        }


        return "redirect:/dashboard?tab=work_types";


    }


    @PostMapping("/delete")
    public String deleteWorktype(
            @ModelAttribute WorkTypeDto workTypeDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            workTypeRepository.deleteWorkType(workTypeDto.getId());


            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Work type has been deleted successfully."
            );

        } catch (Exception e) {
            System.err.println("Error deleting work type: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to delete work type. Details: " + e.getMessage()
            );
        }



        return "redirect:/dashboard?tab=work_types";
    }

    @PostMapping("/edit")
    public String editWorkType(
            @ModelAttribute WorkTypeDto workTypeDto,
            RedirectAttributes redirectAttributes) {

        try {
            workTypeRepository.editWorkType(
                    workTypeDto.getId(),
                    workTypeDto.getName(),
                    workTypeDto.getDescription()
            );

            redirectAttributes.addFlashAttribute("successMessage",
                    "Dane typa pracy (ID: " + workTypeDto.getId() + ") zostały zaktualizowane.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error editing work type: " + e.getMessage());
        }

        return "redirect:/dashboard?tab=work_types";
    }
}
