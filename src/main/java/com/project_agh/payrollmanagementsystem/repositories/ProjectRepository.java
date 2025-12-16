package com.project_agh.payrollmanagementsystem.repositories;
import com.project_agh.payrollmanagementsystem.entities.Project;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository {
    Optional<Project> findById(Long id);
    List<Project> findAll();
    void removeUserFromProject(Long projectId, Long userId);
    void addUserToProject(Long project_id, Long user_id,  String projectRole, LocalDate userAdded );
    void createProject(String project_name, String project_desc, LocalDate project_start_date, LocalDate project_end_date);
    void deleteProject(Long id);
    void editProject(Long id, String name, String description, LocalDate project_start_date, LocalDate project_end_date);
}
