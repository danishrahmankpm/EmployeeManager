package com.example.EmployeeManager.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.EmployeeManager.Dto.EmployeeDto;
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


    public void changeDepartment(UUID id, UUID departmentId) {
        
        throw new UnsupportedOperationException("Unimplemented method 'changeDepartment'");
    }

    public Page<Employee> getAll(Pageable pageable) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    public List<Employee> getAllNameAndId() {
        
        throw new UnsupportedOperationException("Unimplemented method 'getAllNameAndId'");
    }

    public Employee create(EmployeeDto employeeDto) throws NotFoundException {
        return employeeRepository.save(employeeMapper.toEntity(employeeDto));

    }

    public Employee update(UUID id, EmployeeDto dto) throws NotFoundException {
        Employee existing = employeeRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
        // Map updated fields onto the existing entity
        employeeMapper.updateEntityFromDto(dto, existing);
        Employee saved = employeeRepository.save(existing);
        return saved;
    }
    
}
