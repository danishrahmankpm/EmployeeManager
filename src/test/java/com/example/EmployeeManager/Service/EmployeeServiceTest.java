package com.example.EmployeeManager.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.EmployeeManager.Dto.EmployeeDto;
import com.example.EmployeeManager.Dto.EmployeeNameIdDto;
import com.example.EmployeeManager.Mapper.EmployeeMapper;
import com.example.EmployeeManager.Model.Department;
import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Repository.DepartmentRepository;
import com.example.EmployeeManager.Repository.EmployeeRepository;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock private EmployeeRepository employeeRepository;
    @Mock private DepartmentRepository departmentRepository;
    @Mock private EmployeeMapper employeeMapper;

    @InjectMocks private EmployeeService employeeService;

    private EmployeeDto employeeDto;
    private Employee employee;
    private UUID deptId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        employeeDto = new EmployeeDto();
        employeeDto.setName("Aarav Sharma");
        employeeDto.setSalary(BigDecimal.valueOf(60000));
        employeeDto.setJoiningDate(LocalDate.of(2022, 1, 1));
        
        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Aarav Sharma");
    }
    @Test
    void testCreateEmployeeSuccess() throws NotFoundException {
        
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        EmployeeDto result = employeeService.create(employeeDto);

        assertEquals("Aarav Sharma", result.getName());
        verify(employeeRepository).save(employee);
    }
    
    
    @Test
    void testUpdateEmployeeSuccess() throws NotFoundException {
        UUID employeeId = UUID.randomUUID();
        
        
        employee.setId(employeeId);
        employee.setName("Old Name");

        employeeDto.setName("Updated Name");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        
        doAnswer(invocation -> {
            employee.setName(employeeDto.getName());
            return employee;
        }).when(employeeMapper).updateEntityFromDto(employeeDto, employee);

        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toDto(employee)).thenReturn(employeeDto);

        
        EmployeeDto result = employeeService.update(employeeId, employeeDto);

        assertEquals("Updated Name", result.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    void updateEmployeeDepartmentSuccess() throws NotFoundException {
        UUID employeeId = UUID.randomUUID();
        UUID newDeptId = UUID.randomUUID();
        
        Employee existingEmployee = new Employee();
        existingEmployee.setId(employeeId);
        existingEmployee.setName("Aarav Sharma");
        
        Department newDepartment = new Department();
        newDepartment.setId(newDeptId);
        existingEmployee.setDepartment(newDepartment);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setName("Aarav Sharma");
        employeeDto.setDepartment(existingEmployee.getDepartment());
        employeeDto.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(departmentRepository.findById(newDeptId)).thenReturn(Optional.of(newDepartment));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);
        when(employeeMapper.toDto(existingEmployee)).thenReturn(employeeDto);
        
        EmployeeDto updatedEmployee= employeeService.updateEmployeeDepartment(employeeId, newDeptId);
        assertEquals(updatedEmployee.getDepartment(), newDepartment);
        
    }
    @Test
    void testGetAllEmployees() {
        
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toDtoList(any())).thenReturn(List.of(employeeDto));

        Page<EmployeeDto> result = employeeService.getAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Aarav Sharma", result.getContent().get(0).getName());
        verify(employeeRepository).findAll();
    }
    @Test
    void testGetEmployeesById() throws NotFoundException {
        UUID departmentId = UUID.randomUUID();
        
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
        when(employeeRepository.findByDepartmentId(departmentId)).thenReturn(List.of(employee));
        when(employeeMapper.toDtoList(any())).thenReturn(List.of(employeeDto));

        Page<EmployeeDto> result = employeeService.getEmployeesById(departmentId, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Aarav Sharma", result.getContent().get(0).getName());
        verify(employeeRepository).findByDepartmentId(departmentId);
    }
    @Test
    void testGetEmployeeNamesAndIds() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toNameIdDtoList(any())).thenReturn(List.of(new EmployeeNameIdDto("Aarav Sharma", employee.getId().toString())));

        Page<EmployeeNameIdDto> result = employeeService.getEmployeeNamesAndIds(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Aarav Sharma", result.getContent().get(0).getName());
        
    }
}
