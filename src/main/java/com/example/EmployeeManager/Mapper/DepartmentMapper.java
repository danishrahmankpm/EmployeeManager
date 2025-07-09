package com.example.EmployeeManager.Mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.EmployeeManager.Dto.DepartmentDto;
import com.example.EmployeeManager.Model.Department;

@Component
public class DepartmentMapper {

    private final ModelMapper modelMapper;

    public DepartmentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DepartmentDto toDto(Department entity) {
        return modelMapper.map(entity, DepartmentDto.class);
    }

    public Department toEntity(DepartmentDto dto) {
        return modelMapper.map(dto, Department.class);
    }

    public void updateEntityFromDto(DepartmentDto dto, Department entity) {
        modelMapper.map(dto, entity);
    }

    public void fillMissingDtoFieldsFromEntity(DepartmentDto targetDto, Department sourceEntity) {
        ModelMapper skipNonNullMapper = new ModelMapper();
        skipNonNullMapper.getConfiguration()
            .setPropertyCondition(ctx -> ctx.getDestination() == null);

        skipNonNullMapper.map(sourceEntity, targetDto);
    }
    public List<DepartmentDto> toDtoList(List<Department> departments) {
        return departments.stream()
            .map(this::toDto)
            .toList();
    }
    public List<Department> toEntityList(List<DepartmentDto> departmentDtos) {
        return departmentDtos.stream()
            .map(this::toEntity)
            .toList();
    }
}

