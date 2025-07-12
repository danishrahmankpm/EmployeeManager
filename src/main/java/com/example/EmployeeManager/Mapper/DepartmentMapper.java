package com.example.EmployeeManager.Mapper;

import java.util.List;


import org.springframework.stereotype.Component;

import com.example.EmployeeManager.Dto.DepartmentDto.DepartmentRequestDto;
import com.example.EmployeeManager.Dto.DepartmentDto.DepartmentResponseDto;
import com.example.EmployeeManager.Model.Department;


@Component
public class DepartmentMapper {

    

    public DepartmentResponseDto toResponseDto(Department entity) {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setName(entity.getName());
        dto.setCreationDate(entity.getCreationDate());
        dto.setId(entity.getId().toString());
        if(entity.getHead()!= null) {
            dto.setHeadId(entity.getHead().getId().toString());
        }   
        return dto;
    }

    public Department toEntity(DepartmentRequestDto dto) {
        Department department = new Department();
        department.setName(dto.getName());
        department.setCreationDate(dto.getCreationDate());
        return department;
    }

    public void updateEntityFromRequestDto(DepartmentRequestDto dto, Department entity) {
        entity.setName(dto.getName());
        entity.setCreationDate(dto.getCreationDate());
    }

    public void updateDtoFromEntity(Department entity, DepartmentRequestDto dto) {
        dto.setName(entity.getName());
        dto.setCreationDate(entity.getCreationDate());
    }
    public List<DepartmentResponseDto> toResponseDtoList(List<Department> departments) {
        return departments.stream()
            .map(this::toResponseDto)
            .toList();
    }
    public List<Department> toEntityList(List<DepartmentRequestDto> departmentDtos) {
        return departmentDtos.stream()
            .map(this::toEntity)
            .toList();
    }

    
}

