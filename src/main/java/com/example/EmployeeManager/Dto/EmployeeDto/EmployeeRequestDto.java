package com.example.EmployeeManager.Dto.EmployeeDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class EmployeeRequestDto {
    
    
    private String name;
    private LocalDate dob;
    private BigDecimal salary;
    private LocalDate joiningDate;
    private Double yearlyBonusPercent;
    private String address;
    private String title;
    
    
    
}
