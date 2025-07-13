package com.example.EmployeeManager.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeNameIdDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeRequestDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeResponseDto;
import com.example.EmployeeManager.Mapper.DepartmentMapper;
import com.example.EmployeeManager.Mapper.EmployeeMapper;
import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Repository.DepartmentRepository;
import com.example.EmployeeManager.Repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;


    public EmployeeResponseDto create(EmployeeRequestDto employeeDto) throws NotFoundException {
        return employeeMapper.toResponseDto(employeeRepository.save(employeeMapper.toEntity(employeeDto)));

    }

    public EmployeeResponseDto update(UUID id, EmployeeRequestDto dto) throws NotFoundException {
        Employee existing = employeeRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
        employeeMapper.updateEntityFromRequestDto(dto, existing);
        Employee saved = employeeRepository.save(existing);
        return employeeMapper.toResponseDto(saved);
    }
    
    public EmployeeResponseDto updateEmployeeDepartment(UUID id, UUID departmentId) throws NotFoundException {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        
        if (departmentRepository.findById(departmentId).isEmpty()) {
            throw new NotFoundException();
        }
        
        employee.setDepartment(departmentRepository.findById(departmentId).get());
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponseDto(savedEmployee);
    } 

    public Page<EmployeeResponseDto> getEmployeesById(UUID departmentId,Pageable pageable) throws NotFoundException {
        if (departmentRepository.findById(departmentId).isEmpty()) {
            throw new NotFoundException();
        }
        
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        List<EmployeeResponseDto> employeeDtos = employeeMapper.toResponseDtoList(employees);
        Page<EmployeeResponseDto> employeePage = new PageImpl<>(employeeDtos, pageable, employeeDtos.size());
        return employeePage;
    }
    
    public Page<EmployeeNameIdDto> getAllNamesAndIds(Pageable pageable,boolean lookup) throws IllegalArgumentException {
        if(lookup){
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeNameIdDto> employeeDtos = employeeMapper.toNameIdDtoList(employees);
        Page<EmployeeNameIdDto> employeePage = new PageImpl<>(employeeDtos, pageable, employeeDtos.size());
        return employeePage;
        }
        else{
            throw new IllegalArgumentException("Lookup parameter must be true to fetch names and ids");
        } 
    }

    public void deleteAll()throws NotFoundException {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new NotFoundException();
        }
        employeeRepository.deleteAll(employees);
    }

    public EmployeeResponseDto updateEmployeeManager(UUID id, UUID managerId) throws NotFoundException {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        
        if (employeeRepository.findById(managerId).isEmpty()) {
            throw new NotFoundException();
        }   
        
        employee.setManager(employeeRepository.findById(managerId).get());
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponseDto(savedEmployee);
    }

    public void delete(UUID id) throws NotFoundException {
       
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
        
    }

    public Page<EmployeeResponseDto> getAll(Pageable pageable) {
        
        List<Employee> employees= employeeRepository.findAll();
        List<EmployeeResponseDto> employeeDtos = employeeMapper.toResponseDtoList(employees);
        Page<EmployeeResponseDto> employeePage = new PageImpl<>(employeeDtos, pageable, employeeDtos.size());
        return employeePage;
    }



    
}
