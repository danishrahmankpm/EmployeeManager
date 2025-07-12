package com.example.EmployeeManager.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

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

import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeNameIdDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeRequestDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeResponseDto;
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

    private EmployeeResponseDto employeeResponseDto;
    private EmployeeRequestDto employeeRequestDto;
    private Employee employee;
    private UUID deptId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        employeeResponseDto = new EmployeeResponseDto();
        employeeResponseDto.setName("Aarav Sharma");
        
        employeeRequestDto=new EmployeeRequestDto();
        employeeRequestDto.setName("Aarav Sharma");

        employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("Aarav Sharma");
    }
    @Test
    void testCreateEmployeeSuccess() throws NotFoundException {
        
        when(employeeMapper.toEntity(employeeRequestDto)).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        EmployeeResponseDto result = employeeService.create(employeeRequestDto);

        assertEquals("Aarav Sharma", result.getName());
        verify(employeeRepository).save(employee);
    }
    
    
    @Test
    void testUpdateEmployeeSuccess() throws NotFoundException {
        UUID employeeId = employee.getId();
        employeeResponseDto.setName("Rohith Sharma");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        
        doAnswer(invocation -> {
            employee.setName(employeeResponseDto.getName());
            return employee;
        }).when(employeeMapper).updateEntityFromRequestDto(employeeRequestDto, employee);

        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        
        EmployeeResponseDto result = employeeService.update(employeeId, employeeRequestDto);

        assertEquals("Rohith Sharma", result.getName());
        verify(employeeRepository).save(employee);
    }

    @Test
    void updateEmployeeDepartmentSuccess() throws NotFoundException {
        
        UUID newDeptId = UUID.randomUUID();
        Department newDepartment = new Department();
        newDepartment.setId(newDeptId);
        employee.setDepartment(newDepartment);
        employeeResponseDto.setDepartmentId(newDeptId.toString());
        

        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
        when(departmentRepository.findById(newDeptId)).thenReturn(Optional.of(newDepartment));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);
        
        EmployeeResponseDto updatedEmployee= employeeService.updateEmployeeDepartment(employee.getId(), newDeptId);
        assertEquals(updatedEmployee.getDepartmentId(), newDepartment.getId().toString());
        
    }
    @Test
    void testGetAllEmployees() {
        
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toResponseDtoList(any())).thenReturn(List.of(employeeResponseDto));

        Page<EmployeeResponseDto> result = employeeService.getAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Aarav Sharma", result.getContent().get(0).getName());
        verify(employeeRepository).findAll();
    }
    @Test
    void testGetEmployeesById() throws NotFoundException {
        UUID departmentId = UUID.randomUUID();
        
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
        when(employeeRepository.findByDepartmentId(departmentId)).thenReturn(List.of(employee));
        when(employeeMapper.toResponseDtoList(any())).thenReturn(List.of(employeeResponseDto));

        Page<EmployeeResponseDto> result = employeeService.getEmployeesById(departmentId, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Aarav Sharma", result.getContent().get(0).getName());
        verify(employeeRepository).findByDepartmentId(departmentId);
    }
    @Test
    void testGetEmployeeNamesAndIds() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toNameIdDtoList(any())).thenReturn(List.of(new EmployeeNameIdDto("Aarav Sharma", employee.getId().toString())));

        Page<EmployeeNameIdDto> result = employeeService.getAllNamesAndIds(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Aarav Sharma", result.getContent().get(0).getName());
        
    }
}
