package com.example.EmployeeManager.Dto.DepartmentDto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DepartmentResponseDto {
    String id;
    String name;
    LocalDate creationDate;
    String headId;
    String headName;
}
