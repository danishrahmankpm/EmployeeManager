package com.example.EmployeeManager.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.EmployeeManager.Dto.EmployeeDto;
import com.example.EmployeeManager.Dto.EmployeeNameIdDto;
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


    

    public Page<EmployeeDto> getAll(Pageable pageable) {
        // This method should return a paginated list of employees
        List<Employee> employees= employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = employeeMapper.toDtoList(employees);
        Page<EmployeeDto> employeePage = new PageImpl<>(employeeDtos, pageable, employeeDtos.size());
        return employeePage;
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
    
    public Employee updateEmplyeeDepartment(UUID id, UUID departmentId) throws NotFoundException {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        
        if (departmentRepository.findById(departmentId).isEmpty()) {
            throw new NotFoundException();
        }
        
        employee.setDepartment(departmentRepository.findById(departmentId).get());
        return employeeRepository.save(employee);
    } 

    public Page<EmployeeDto> getEmployeesById(UUID departmentId,Pageable pageable) throws NotFoundException {
        if (departmentRepository.findById(departmentId).isEmpty()) {
            throw new NotFoundException();
        }
        
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        List<EmployeeDto> employeeDtos = employeeMapper.toDtoList(employees);
        Page<EmployeeDto> employeePage = new PageImpl<>(employeeDtos, pageable, employeeDtos.size());
        return employeePage;
    }
    public Page<EmployeeNameIdDto> getEmployeeNamesAndIds(Pageable pageable) {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeNameIdDto> employeeDtos = employeeMapper.toNameIdDtoList(employees);
        Page<EmployeeNameIdDto> employeePage = new PageImpl<>(employeeDtos, pageable, employeeDtos.size());
        return employeePage;
    }
    
}
