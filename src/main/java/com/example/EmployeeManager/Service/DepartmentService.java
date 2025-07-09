package com.example.EmployeeManager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeManager.Mapper.DepartmentMapper;
import com.example.EmployeeManager.Mapper.EmployeeMapper;
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

    
    
}
