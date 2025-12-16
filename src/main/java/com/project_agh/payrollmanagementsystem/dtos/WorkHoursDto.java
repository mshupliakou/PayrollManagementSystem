package com.project_agh.payrollmanagementsystem.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class WorkHoursDto {
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;

    private Long userId;
    private Long projectId;
    private Long workTypeId;
    private String comment;
}
