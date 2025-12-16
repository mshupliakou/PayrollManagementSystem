package com.project_agh.payrollmanagementsystem.controller;

import com.project_agh.payrollmanagementsystem.dtos.WorkHoursDto;
import com.project_agh.payrollmanagementsystem.entities.User;
import com.project_agh.payrollmanagementsystem.repositories.UserRepository;
import com.project_agh.payrollmanagementsystem.repositories.WorkHoursRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class WorkHoursController {

    private final WorkHoursRepository workHoursRepository;
    private final UserRepository userRepository; // Potrzebne do pobrania ID usera

    public WorkHoursController(WorkHoursRepository workHoursRepository, UserRepository userRepository) {
        this.workHoursRepository = workHoursRepository;
        this.userRepository = userRepository;


    }

    @PostMapping("/work_hours/create")
    public String createWorkHours(
            @ModelAttribute WorkHoursDto workHoursDto,
            RedirectAttributes redirectAttributes) {

        try {
            // 1. Pobieramy aktualnie zalogowanego użytkownika
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 2. Sprawdzamy czy wybrano projekt (czy ID nie jest puste/null)
            Long projectId = workHoursDto.getProjectId();

            // Logika: Jeśli wybrano projekt, używamy metody z projektem. Jeśli nie - zwykłej.
            if (projectId != null) {
                workHoursRepository.createWorkHoursWithProject(
                        currentUser.getId(), // ID zalogowanego usera, a nie z DTO
                        workHoursDto.getDate(),
                        workHoursDto.getWorkTypeId(),
                        projectId,
                        workHoursDto.getStartTime(),
                        workHoursDto.getEndTime(),
                        workHoursDto.getComment()
                );
            } else {
                workHoursRepository.createWorkHours(
                        currentUser.getId(),
                        workHoursDto.getDate(),
                        workHoursDto.getWorkTypeId(),
                        workHoursDto.getStartTime(),
                        workHoursDto.getEndTime(),
                        workHoursDto.getComment()
                );
            }

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Godziny pracy zostały dodane pomyślnie."
            );

        } catch (Exception e) {
            e.printStackTrace(); // Warto widzieć błąd w konsoli
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Błąd: Nie udało się dodać godzin. Szczegóły: " + e.getMessage()
            );
        }

        // Wracamy na zakładkę godzin pracy
        return "redirect:/dashboard?tab=work_hours";
    }

    @PostMapping("/work_hours/edit")
    public String editWorkHours(
            @ModelAttribute WorkHoursDto workHoursDto,
            RedirectAttributes redirectAttributes) {

        try {
            workHoursRepository.editWorkHours(
                    workHoursDto.getId(),
                    workHoursDto.getUserId(),
                    workHoursDto.getDate(),
                    workHoursDto.getWorkTypeId(),
                    workHoursDto.getStartTime(),
                    workHoursDto.getEndTime(),
                    workHoursDto.getComment()
            );

            redirectAttributes.addFlashAttribute("successMessage",
                    "Dane  (ID: " + workHoursDto.getId() + ") zostały zaktualizowane.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error editing work type: " + e.getMessage());
        }

        return "redirect:/dashboard?tab=work_hours";
     }


    @PostMapping("/work_hours/delete")
    public String deleteWorkHours(
            @ModelAttribute WorkHoursDto workHoursDto,
            RedirectAttributes redirectAttributes) {
        try {
            workHoursRepository.deleteWorkHours(workHoursDto.getId());


            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Work hour has been deleted successfully."
            );

        } catch (Exception e) {
            System.err.println("Error deleting work hour: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to delete work hour. Details: " + e.getMessage()
            );
        }



        return "redirect:/dashboard?tab=work_hours";
    }



    @PostMapping("admin/work_hours/approve")
    public String approveWorkHours(
            @ModelAttribute WorkHoursDto workHoursDto,
            RedirectAttributes redirectAttributes) {
        try {
            workHoursRepository.approveWorkHours(workHoursDto.getId());


            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Work hour has been deleted successfully."
            );

        } catch (Exception e) {
            System.err.println("Error deleting work hour: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to delete work hour. Details: " + e.getMessage()
            );
        }



        return "redirect:/dashboard?tab=approvals";
    }


}