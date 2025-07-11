package com.example.EmployeeManager.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
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
        employeeDto.setDepartmentId(deptId);
        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Aarav Sharma");
    }
    @Test
    void testCreateEmployeeSuccess() throws NotFoundException {
        
        when(employeeMapper.toEntity(employeeDto)).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee result = employeeService.create(employeeDto);

        assertEquals("Aarav Sharma", result.getName());
        verify(employeeRepository).save(employee);
    }
    
    
    @Test
    void testUpdateEmployeeSuccess() throws NotFoundException {
        UUID employeeId = UUID.randomUUID();
        
        // Existing employee fetched from DB
        employee.setId(employeeId);
        employee.setName("Old Name");

        // DTO with updated name
        employeeDto.setName("Updated Name");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        // Simulate mapper updating entity fields from dto
        doAnswer(invocation -> {
            employee.setName(employeeDto.getName());
            return employee;
        }).when(employeeMapper).updateEntityFromDto(employeeDto, employee);

        when(employeeRepository.save(employee)).thenReturn(employee);

        // Call the actual service method
        Employee result = employeeService.update(employeeId, employeeDto);

        assertEquals("Updated Name", result.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
   void updateEmployeeDepartmentSuccess() throws NotFoundException {
        UUID employeeId = UUID.randomUUID();
        UUID newDeptId = UUID.randomUUID();
        
        // Existing employee fetched from DB
        employee.setId(employeeId);
        
        
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(newDeptId)).thenReturn(Optional.of(new Department()));
        
        Department newDepartment = new Department();
        newDepartment.setId(newDeptId);
        when(departmentRepository.findById(newDeptId)).thenReturn(Optional.of(newDepartment));
        // Set department directly since employee is not a mock
        employee.setDepartment(newDepartment);

        when(employeeRepository.save(employee)).thenReturn(employee);
        
        Employee result = employeeService.updateEmplyeeDepartment(employeeId, newDeptId);

        assertEquals(newDeptId, result.getDepartment().getId());
        verify(employeeRepository).save(employee);
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
        
        // Mocking the department existence
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
        
        // Mocking employee retrieval
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
