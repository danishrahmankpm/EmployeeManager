package com.example.EmployeeManager.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.EmployeeManager.Dto.EmployeeDto;
import com.example.EmployeeManager.Model.Employee;

public class EmployeeService {

    public void changeDepartment(UUID id, UUID departmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeDepartment'");
    }

    public Page<Employee> getAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    public List<Employee> getAllNameAndId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllNameAndId'");
    }

    public Employee create(EmployeeDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    public Employee update(UUID id, EmployeeDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
