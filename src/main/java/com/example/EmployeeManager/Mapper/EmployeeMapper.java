package com.example.EmployeeManager.Mapper;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Component;

import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeNameIdDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeRequestDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeResponseDto;


@Component
public class EmployeeMapper {
    public EmployeeMapper() {
    }
    
    public Employee toEntity(EmployeeRequestDto dto) {
        Employee entity = new Employee();
        entity.setName(dto.getName());
        entity.setDob(dto.getDob());
        entity.setSalary(dto.getSalary());
        entity.setJoiningDate(dto.getJoiningDate());
        entity.setYearlyBonusPercent(dto.getYearlyBonusPercent());
        entity.setAddress(dto.getAddress());
        entity.setTitle(dto.getTitle());
        return entity;
    }

    

    public void updateEntityFromRequestDto(EmployeeRequestDto dto, Employee entity) {
        entity.setName(dto.getName());
        entity.setDob(dto.getDob());
        entity.setSalary(dto.getSalary());
        entity.setJoiningDate(dto.getJoiningDate());
        entity.setYearlyBonusPercent(dto.getYearlyBonusPercent());
        entity.setAddress(dto.getAddress());
        entity.setTitle(dto.getTitle());
    
    }

    

    

    public List<Employee> toEntityList(List<EmployeeRequestDto> employeeDtos) {
        return employeeDtos.stream()
            .map(this::toEntity)
            .toList();
    }

    public List<EmployeeNameIdDto> toNameIdDtoList(List<Employee> employees) {
        return employees.stream()
            .map(employee -> {
                EmployeeNameIdDto dto = new EmployeeNameIdDto();
                dto.setName(employee.getName());
                dto.setId(employee.getId().toString());
                return dto;
            })
            .toList();
    }

    public EmployeeResponseDto toResponseDto(Employee employee) {
       
        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setId(employee.getId().toString());
        responseDto.setName(employee.getName());
        responseDto.setDob(employee.getDob());
        responseDto.setSalary(employee.getSalary());
        responseDto.setJoiningDate(employee.getJoiningDate());
        responseDto.setYearlyBonusPercent(employee.getYearlyBonusPercent());
        responseDto.setAddress(employee.getAddress());
        responseDto.setTitle(employee.getTitle());
        if (employee.getDepartment() != null) {
            responseDto.setDepartmentId(employee.getDepartment().getId().toString());
            responseDto.setDepartmentName(employee.getDepartment().getName());
        }else{
            responseDto.setDepartmentId(null);
            responseDto.setDepartmentName(null);
        }
        if(employee.getManager() != null) {
            responseDto.setManagerId(employee.getManager().getId().toString());
            responseDto.setManagerName(employee.getManager().getName());
        }else{
            responseDto.setManagerId(null);
            responseDto.setManagerName(null);
        }
        return responseDto;
    }

    public List<EmployeeResponseDto> toResponseDtoList(List<Employee> employees) {
        return employees.stream()
            .map(this::toResponseDto)
            .toList();
    }

    public List<EmployeeNameIdDto> toEmployeeNameIdDtoList(List<Employee> employees) {
        List<EmployeeNameIdDto> employeeNameIdDtos = new ArrayList<>();
        for(Employee employee : employees) {
            EmployeeNameIdDto dto = new EmployeeNameIdDto();
            dto.setName(employee.getName());
            dto.setId(employee.getId().toString());
            employeeNameIdDtos.add(dto);
        }
        return employeeNameIdDtos;
    }

    

   
}
