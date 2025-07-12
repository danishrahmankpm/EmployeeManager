package com.example.EmployeeManager.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.EmployeeManager.Dto.DepartmentDto.DepartmentRequestDto;
import com.example.EmployeeManager.Dto.DepartmentDto.DepartmentResponseDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeNameIdDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeRequestDto;
import com.example.EmployeeManager.Mapper.DepartmentMapper;
import com.example.EmployeeManager.Mapper.EmployeeMapper;
import com.example.EmployeeManager.Model.Department;
import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Repository.DepartmentRepository;
import com.example.EmployeeManager.Repository.EmployeeRepository;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    
    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentDto) {
        return departmentMapper.toResponseDto(departmentRepository.save(departmentMapper.toEntity(departmentDto)));
        
    }

    public void deleteDepartment(UUID id) throws NotFoundException {
        if(departmentRepository.findById(id).isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    public DepartmentResponseDto updateDepartment(UUID id, DepartmentRequestDto departmentDto) throws NotFoundException {
        Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        
        departmentMapper.updateEntityFromRequestDto(departmentDto, existing);
        return departmentMapper.toResponseDto(departmentRepository.save(existing));
    }

    public Page<DepartmentResponseDto> getAll(Pageable pageable) {
        
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDto> departmentDtos = departmentMapper.toResponseDtoList(departments);
        Page<DepartmentResponseDto> departmentPage = new PageImpl<>(departmentDtos, pageable, departments.size());
        return departmentPage;
    }

    public DepartmentResponseDto getDepartmentById(UUID id) throws NotFoundException {
        
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        
            return departmentMapper.toResponseDto(department);
    }

    public Page<EmployeeNameIdDto> getEmployeesByDepartment(UUID departmentId,Pageable pageable) throws NotFoundException {
        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new NotFoundException());
        
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        if (employees.isEmpty()) {
            throw new NotFoundException();
        }
        List<EmployeeNameIdDto> employeeDtos = employeeMapper.toEmployeeNameIdDtoList(employees);
        Page<EmployeeNameIdDto> employeePage = new PageImpl<>(employeeDtos, pageable, employees.size());
        return employeePage;
    }

    
}
