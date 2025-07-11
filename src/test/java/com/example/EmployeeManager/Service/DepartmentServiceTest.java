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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.EmployeeManager.Dto.DepartmentDto;
import com.example.EmployeeManager.Dto.EmployeeDto;
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

    
    @Test
    void testCreateDepartmentSuccess() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Engineering");

        Department department = new Department();
        department.setName("Engineering");

        when(departmentMapper.toEntity(departmentDto)).thenReturn(department);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department result = departmentService.createDepartment(departmentDto);

        assertEquals("Engineering", result.getName());
        verify(departmentRepository).save(department);
    }
    @Test
    void testUpdateDepartmentSuccess() throws NotFoundException {
        UUID id = UUID.randomUUID();
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Engineering");

        Department existingDepartment = new Department();
        existingDepartment.setId(id);
        existingDepartment.setName("Engineering");

        when(departmentRepository.findById(id)).thenReturn(Optional.of(existingDepartment));
      
        doNothing().when(departmentMapper).updateEntityFromDto(departmentDto, existingDepartment);
        when(departmentRepository.save(existingDepartment)).thenReturn(existingDepartment);

        Department result = departmentService.updateDepartment(id, departmentDto);

        assertEquals("Engineering", result.getName());
        verify(departmentRepository).save(existingDepartment);
    }
    @Test
    void testDeleteDepartmentSuccess() throws NotFoundException {
        UUID id = UUID.randomUUID();
        Department department = new Department();
        department.setId(id);

        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).deleteById(id);

        departmentService.deleteDepartment(id);

        verify(departmentRepository).deleteById(id);
    }
    @Test
    void testGetDepartmentByIdSuccess() throws NotFoundException {
        UUID id = UUID.randomUUID();
        Department department = new Department();
        department.setId(id);
        department.setName("Engineering");
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(id);
        departmentDto.setName("Engineering");

        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));
        when(departmentMapper.toDto(department)).thenReturn(departmentDto);

        DepartmentDto result = departmentService.getDepartmentById(id);

        assertEquals("Engineering", result.getName());
        verify(departmentRepository).findById(id);
    }
    @Test
    void testGetAllDepartmentsSuccess() {
        Department department = new Department();
        department.setName("Engineering");
        department.setId(UUID.randomUUID());

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName("Engineering");
        departmentDto.setId(department.getId());

        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(departmentMapper.toDtoList(List.of(department))).thenReturn(List.of(departmentDto));

        Page<DepartmentDto> result = departmentService.getAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Engineering", result.getContent().get(0).getName());
    }
    @Test
    void testGetEmployeesByDepartmentSuccess() throws NotFoundException {
        UUID departmentId = UUID.randomUUID();
        Department department = new Department();
        department.setId(departmentId);
        department.setName("Engineering");
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("John Doe");
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setName("John Doe");
        
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        // Return an empty list of Employee as a placeholder; adjust as needed for your test
        when(employeeRepository.findByDepartmentId(departmentId)).thenReturn(List.of(employee));
        when(employeeMapper.toDtoList(List.of(employee))).thenReturn(List.of(employeeDto));

        Page<EmployeeDto> result = departmentService.getEmployeesByDepartment(departmentId, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
    }


}
