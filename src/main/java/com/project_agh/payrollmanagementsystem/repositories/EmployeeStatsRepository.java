package com.project_agh.payrollmanagementsystem.repositories;

import com.project_agh.payrollmanagementsystem.entities.EmployeeStats;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeStatsRepository {
    Double sumAllById(Long id);
    Double weekAverageById(Long id);
     Optional<EmployeeStats> findById(Long id);
}
