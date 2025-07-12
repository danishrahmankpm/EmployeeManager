package com.example.EmployeeManager.Controller;


import com.example.EmployeeManager.Dto.DepartmentDto.DepartmentResponseDto;
import com.example.EmployeeManager.Model.Department;
import com.example.EmployeeManager.Service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private DepartmentService departmentService;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void testCreateDepartment() throws Exception {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setName("Engineering");
        dto.setCreationDate(LocalDate.now());
        Department department = new Department();
        department.setId(UUID.randomUUID());
        department.setName(dto.getName());

        Mockito.when(departmentService.createDepartment(any())).thenReturn(dto);

        mockMvc.perform(post("/departments/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    void testGetDepartmentByIdSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setId(id.toString());
        dto.setName("HR");

        Mockito.when(departmentService.getDepartmentById(id)).thenReturn(dto);

        mockMvc.perform(get("/departments/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("HR"));
    }

    @Test
    void testDeleteDepartmentSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/departments/{id}", id))
            .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateDepartment() throws Exception {
        UUID id = UUID.randomUUID();
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setName("UpdatedDept");
        dto.setCreationDate(LocalDate.now());
        

        Mockito.when(departmentService.updateDepartment(eq(id), any())).thenReturn(dto);

        mockMvc.perform(post("/departments/{id}/update", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("UpdatedDept"));
    }

    @Test
    void testGetAllDepartments() throws Exception {
        DepartmentResponseDto dto = new DepartmentResponseDto();
        dto.setName("Finance");
        dto.setCreationDate(LocalDate.now());
        Page<DepartmentResponseDto> page = new PageImpl<>(Collections.singletonList(dto));

        Mockito.when(departmentService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/departments/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].name").value("Finance"));
    }
}

