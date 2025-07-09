package com.example.EmployeeManager.Dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.example.EmployeeManager.Model.Employee;

import lombok.Data; 

@Data
public class DepartmentDto {
       
    
    private UUID id;
    private String name;
    private LocalDate creationDate;
    private Employee head;
    private List<Employee> employees;
    
    

}
