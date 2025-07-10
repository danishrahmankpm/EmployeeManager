package com.example.EmployeeManager.Service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.EmployeeManager.Dto.DepartmentDto;
import com.example.EmployeeManager.Dto.EmployeeDto;
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

    public Page<DepartmentDto> getAll(Pageable pageable) {
        // This method should return a paginated list of departments
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDto> departmentDtos = departmentMapper.toDtoList(departments);
        Page<DepartmentDto> departmentPage = new PageImpl<>(departmentDtos, pageable, departments.size());
        return departmentPage;
    }

    public DepartmentDto getDepartmentById(UUID id) throws NotFoundException {
        
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException());
        
            return departmentMapper.toDto(department);
    }

    public Page<EmployeeDto> getEmployeesByDepartment(UUID departmentId,Pageable pageable) throws NotFoundException {
        Department department = departmentRepository.findById(departmentId)
            .orElseThrow(() -> new NotFoundException());
        
        List<Employee> employees = employeeRepository.findByDepartmentId(departmentId);
        if (employees.isEmpty()) {
            throw new NotFoundException();
        }
        List<EmployeeDto> employeeDtos = employeeMapper.toDtoList(employees);
        Page<EmployeeDto> employeePage = new PageImpl<>(employeeDtos, pageable, employeeDtos.size());
        return employeePage;
    }

    
}
