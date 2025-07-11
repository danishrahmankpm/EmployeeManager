package com.example.EmployeeManager.Controller;

import com.example.EmployeeManager.Dto.EmployeeDto;
import com.example.EmployeeManager.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private EmployeeService employeeService;

    private final UUID empId = UUID.randomUUID();
    private final UUID deptId = UUID.randomUUID();

    private EmployeeDto sampleDto() {
        EmployeeDto dto = new EmployeeDto();
        
        dto.setName("Aarav Sharma");
        dto.setSalary(BigDecimal.valueOf(50000));
        dto.setJoiningDate(LocalDate.of(2022, 1, 1));
        
        return dto;
    }

    @Test
    void testCreateEmployee() throws Exception {
        EmployeeDto dto = sampleDto();

        Mockito.when(employeeService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Aarav Sharma"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeDto dto = sampleDto();
        dto.setName("Updated Name");

        Mockito.when(employeeService.update(eq(empId), any())).thenReturn(dto);

        mockMvc.perform(put("/employees/{id}", empId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void testUpdateEmployeeDepartment() throws Exception {
        EmployeeDto dto = sampleDto();
        dto.setName("With New Dept");

        Mockito.when(employeeService.updateEmployeeDepartment(eq(empId), eq(deptId)))
               .thenReturn(dto);

        mockMvc.perform(patch("/employees/{id}/department", empId)
                .param("departmentId", deptId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("With New Dept"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        EmployeeDto dto = sampleDto();
        Page<EmployeeDto> page = new PageImpl<>(Collections.singletonList(dto));

        Mockito.when(employeeService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/employees/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value("Aarav Sharma"));
    }
}
