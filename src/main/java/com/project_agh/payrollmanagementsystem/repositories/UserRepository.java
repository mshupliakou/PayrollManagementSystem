package com.project_agh.payrollmanagementsystem.repositories;

import com.project_agh.payrollmanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} (Employee) entities.
 * <p>
 * This interface extends Spring Data JPA's {@link JpaRepository}, providing
 * standard CRUD operations and custom query capabilities essential for
 * employee and authentication management.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves an Optional container of a User entity based on their unique email address.
     * <p>
     * This method is crucial for the authentication process, as the email is used as the login identifier.
     *
     * @param email The unique email address of the User to look for.
     * @return An {@link Optional} containing the matching User, or empty if no User is found.
     */
    Optional<User> findByEmail(String email);
}