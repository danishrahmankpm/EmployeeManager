package com.example.EmployeeManager.Dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.example.EmployeeManager.Model.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
       
    
    private UUID id;
    private String name;
    private LocalDate creationDate;
    private Employee head;
    private List<Employee> employees;
    
    
}

