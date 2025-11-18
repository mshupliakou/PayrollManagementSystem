package com.project_agh.payrollmanagementsystem.repositories;
import com.project_agh.payrollmanagementsystem.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Role} entities.
 * <p>
 * This interface extends Spring Data JPA's {@link JpaRepository}, providing
 * standard CRUD (Create, Read, Update, Delete) operations and basic querying
 * capabilities for the Role entity, using {@code Long} as the type
 * of the primary key (ID).
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Custom query methods, such as finding a Role by its name, can be added here.
    // Example:
    // Optional<Role> findByName(String name);
}