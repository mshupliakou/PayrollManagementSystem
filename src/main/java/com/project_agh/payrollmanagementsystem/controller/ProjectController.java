package com.project_agh.payrollmanagementsystem.controller;

import com.project_agh.payrollmanagementsystem.dtos.ProjectDto;
import com.project_agh.payrollmanagementsystem.repositories.ProjectRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("admin/projects")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProjectController {


    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

    @PostMapping("/create")
    public String createProject(
            @ModelAttribute ProjectDto projectDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            projectRepository.createProject(
                    projectDto.getName(),
                    projectDto.getDescription(),
                    projectDto.getProjectBeginDate(),
                    projectDto.getProjectEndDate()
            );

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Project has been created successfully."
            );

        } catch (Exception e) {
            System.err.println("Error creating project: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to create project. Details: " + e.getMessage()
            );
        }


        return "redirect:/dashboard?tab=projects";


    }


    @PostMapping("/delete")
    public String deleteProject(
            @ModelAttribute ProjectDto projectDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        try {
            projectRepository.deleteProject(projectDto.getId());


            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Project has been deleted successfully."
            );

        } catch (Exception e) {
            System.err.println("Error deleting project: " + e.getMessage());
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Error: Failed to delete role. Details: " + e.getMessage()
            );
        }



        return "redirect:/dashboard?tab=projects";
    }

    @PostMapping("/edit")
    public String editProject(
            @ModelAttribute ProjectDto projectDto,
            RedirectAttributes redirectAttributes) {

        try {
            projectRepository.editProject(
                    projectDto.getId(),
                    projectDto.getName(),
                    projectDto.getDescription(),
                    projectDto.getProjectBeginDate(),
                    projectDto.getProjectEndDate()
            );

            redirectAttributes.addFlashAttribute("successMessage",
                    "Dane projekta (ID: " + projectDto.getId() + ") zostały zaktualizowane.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Error editing project: " + e.getMessage());
        }

        return "redirect:/dashboard?tab=projects";
    }
}
