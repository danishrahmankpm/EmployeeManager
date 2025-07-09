package com.example.EmployeeManager.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.EmployeeManager.Dto.EmployeeDto;
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
    public void fillMissingDtoFieldsFromEntity(EmployeeDto targetDto, Employee sourceEntity) {
        ModelMapper skipNonNullMapper = new ModelMapper();
        skipNonNullMapper.getConfiguration()
            .setPropertyCondition(ctx -> ctx.getDestination() == null);

        skipNonNullMapper.map(sourceEntity, targetDto);
    }
}
