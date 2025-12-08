package com.project_agh.payrollmanagementsystem.dtos;

import lombok.Data;

@Data
public class PositionDto {
    Long id;
    private String position_name;
    private String position_desc;
}
