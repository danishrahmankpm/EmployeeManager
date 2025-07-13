package com.example.EmployeeManager.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.EmployeeManager.Dto.DepartmentDto.DepartmentRequestDto;
import com.example.EmployeeManager.Dto.DepartmentDto.DepartmentResponseDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeNameIdDto;
import com.example.EmployeeManager.Mapper.DepartmentMapper;
import com.example.EmployeeManager.Mapper.EmployeeMapper;
import com.example.EmployeeManager.Model.Department;
import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Repository.DepartmentRepository;
import com.example.EmployeeManager.Repository.EmployeeRepository;



@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock private DepartmentRepository departmentRepository;
    @Mock private EmployeeRepository employeeRepository;
    @Mock private DepartmentMapper departmentMapper;
    @Mock private EmployeeMapper employeeMapper;

    @InjectMocks private DepartmentService departmentService;

    private DepartmentRequestDto departmentRequestDto;
    private DepartmentResponseDto departmentResponseDto;
    private Department department;
    @BeforeEach
    void setUp() {
        departmentRequestDto = new DepartmentRequestDto();
        departmentRequestDto.setName("Engineering");

        department = new Department();
        department.setId(UUID.randomUUID());
        department.setName("Engineering");

        departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setId(department.getId().toString());
        departmentResponseDto.setName(department.getName());
    }
    
    @Test
    void testCreateDepartmentSuccess() {
        
       

        when(departmentMapper.toEntity(departmentRequestDto)).thenReturn(department);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        when(departmentMapper.toResponseDto(department)).thenReturn(departmentResponseDto);

        DepartmentResponseDto result = departmentService.createDepartment(departmentRequestDto);

        assertEquals("Engineering", result.getName());
        verify(departmentRepository).save(department);
    }

    @Test
    void testUpdateDepartmentSuccess() throws NotFoundException {
        UUID id = department.getId();
        
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
      
        doNothing().when(departmentMapper).updateEntityFromRequestDto(departmentRequestDto, department);
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentMapper.toResponseDto(department)).thenReturn(departmentResponseDto);

        DepartmentResponseDto result = departmentService.updateDepartment(id, departmentRequestDto);

        assertEquals("Engineering", result.getName());
        verify(departmentRepository).save(department);
    }
    @Test
    void testDeleteDepartmentSuccess() throws NotFoundException {
        UUID id = department.getId();
        
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).deleteById(id);

        departmentService.deleteDepartment(id);

        verify(departmentRepository).deleteById(id);
    }
    @Test
    void testGetDepartmentByIdSuccess() throws NotFoundException {
        UUID id = department.getId();

        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        when(departmentMapper.toResponseDto(department)).thenReturn(departmentResponseDto);

        DepartmentResponseDto result = departmentService.getDepartmentById(id);

        assertEquals("Engineering", result.getName());
        verify(departmentRepository).findById(id);
    }
    @Test
    void testGetAllDepartmentsSuccess() {
        

        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(departmentMapper.toResponseDtoList(List.of(department))).thenReturn(List.of(departmentResponseDto));

        Page<DepartmentResponseDto> result = departmentService.getAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Engineering", result.getContent().get(0).getName());
    }
    @Test
    void testGetEmployeesByDepartmentSuccess() throws NotFoundException {
        UUID departmentId= department.getId();
        Employee employee = new Employee();
        employee.setName("John Doe");
        EmployeeNameIdDto employeeDto = new EmployeeNameIdDto();
        employeeDto.setName("John Doe");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        when(employeeRepository.findByDepartmentId(departmentId)).thenReturn(List.of(employee));
        when(employeeMapper.toEmployeeNameIdDtoList(List.of(employee))).thenReturn(List.of(employeeDto));

        Page<EmployeeNameIdDto> result = departmentService.getEmployeesByDepartment(departmentId, Pageable.unpaged(),true);

        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
    }


}
