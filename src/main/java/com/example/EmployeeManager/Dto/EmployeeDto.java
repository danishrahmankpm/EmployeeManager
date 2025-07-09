package com.example.EmployeeManager.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.example.EmployeeManager.Model.Department;
import com.example.EmployeeManager.Model.Employee;
import lombok.Data;

@Data
public class EmployeeDto {
    
    private UUID id;
    private String name;
    private LocalDate dob;
    private BigDecimal salary;
    private LocalDate joiningDate;
    private Double yearlyBonusPercent;
    private String address;
    private String title;
    private Department department;
    private UUID departmentId;
    private Employee manager;
    private UUID managerId;
    
}
