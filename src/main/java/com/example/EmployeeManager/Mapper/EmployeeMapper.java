package com.example.EmployeeManager.Mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.EmployeeManager.Dto.EmployeeDto;
import com.example.EmployeeManager.Dto.EmployeeNameIdDto;
import com.example.EmployeeManager.Model.Employee;


@Component
public class EmployeeMapper {

    private final ModelMapper modelMapper;

    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Employee toEntity(EmployeeDto dto) {
        return modelMapper.map(dto, Employee.class);
    }

    public EmployeeDto toDto(Employee entity) {
        return modelMapper.map(entity, EmployeeDto.class);
    }
    public void updateEntityFromDto(EmployeeDto dto, Employee entity) {
        modelMapper.map(dto, entity);
    }
    public void updateDtoFromEntity(Employee entity, EmployeeDto dto) {
        modelMapper.map(entity, dto);
    }

    public List<EmployeeDto> toDtoList(List<Employee> byDepartmentId) {
        return byDepartmentId.stream()
            .map(this::toDto)
            .toList();
    }
    public List<Employee> toEntityList(List<EmployeeDto> employeeDtos) {
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
    
}
