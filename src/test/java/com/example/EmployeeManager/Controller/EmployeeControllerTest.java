package com.example.EmployeeManager.Controller;


import com.example.EmployeeManager.Config.TestSecurityConfig;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeResponseDto;
import com.example.EmployeeManager.Service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@Import(TestSecurityConfig.class)
class EmployeeControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private EmployeeService employeeService;

    private final UUID empId = UUID.randomUUID();
    private final UUID deptId = UUID.randomUUID();


    @Test
    void testCreateEmployee() throws Exception {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(empId.toString());
        dto.setName("Aarav Sharma");

        Mockito.when(employeeService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Aarav Sharma"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(empId.toString());
        dto.setName("Rohith Sharma");

        Mockito.when(employeeService.update(eq(empId), any())).thenReturn(dto);

        mockMvc.perform(put("/employees/{id}", empId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Rohith Sharma"));
    }

    @Test
    void testUpdateEmployeeDepartment() throws Exception {
        UUID randomId= UUID.randomUUID();
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(empId.toString());
        dto.setName("Aarav Sharma");
        dto.setDepartmentId(randomId.toString());

        Mockito.when(employeeService.updateEmployeeDepartment(eq(empId), eq(deptId)))
               .thenReturn(dto);

        mockMvc.perform(patch("/employees/{id}/department", empId)
                .param("departmentId", deptId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.departmentId").value(randomId.toString()));
    }

    
}
