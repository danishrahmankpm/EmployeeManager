package com.example.EmployeeManager.Dto.DepartmentDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDto {
       
    
    
    private String name;
    private LocalDate creationDate;
    
    
    
}

