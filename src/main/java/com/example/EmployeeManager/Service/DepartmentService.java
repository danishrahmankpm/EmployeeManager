package com.example.EmployeeManager.Service;

import java.util.UUID;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.EmployeeManager.Dto.DepartmentDto;
import com.example.EmployeeManager.Mapper.DepartmentMapper;
import com.example.EmployeeManager.Mapper.EmployeeMapper;
import com.example.EmployeeManager.Model.Department;
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
    
    public Department createDepartment(DepartmentDto departmentDto) {
        return departmentRepository.save(departmentMapper.toEntity(departmentDto));
        
    }

    public void deleteDepartment(UUID id) throws NotFoundException {
        if(departmentRepository.findById(id).isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    public Department updateDepartment(UUID id, DepartmentDto departmentDto) throws NotFoundException {
        Department existing = departmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        
        // Map updated fields onto the existing entity
        departmentMapper.updateEntityFromDto(departmentDto, existing);
        
        return departmentRepository.save(existing);
    }

    
}
