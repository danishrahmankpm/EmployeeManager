package com.example.EmployeeManager.Controller;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeNameIdDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeRequestDto;
import com.example.EmployeeManager.Dto.EmployeeDto.EmployeeResponseDto;
import com.example.EmployeeManager.Service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired private EmployeeService service;

    @PostMapping("/create")
    public ResponseEntity<EmployeeResponseDto> create(@RequestBody EmployeeRequestDto dto) {
        try {
            return ResponseEntity.ok(service.create(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> update(@PathVariable UUID id, @RequestBody EmployeeRequestDto dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }   

    @PatchMapping("/{id}/department")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeDepartment(
            @PathVariable UUID id, @RequestParam UUID departmentId) {
        try {
            return ResponseEntity.ok(service.updateEmployeeDepartment(id, departmentId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<EmployeeResponseDto>> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/allnamesandids")
    public ResponseEntity<Page<EmployeeNameIdDto>>  getAllNamesAndIds(@PageableDefault(page = 0, size = 20) Pageable pageable,@RequestParam(required = true) Boolean lookup) {
        return ResponseEntity.ok(service.getAllNamesAndIds(pageable));
    }
    
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        try {
            service.deleteAll();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}/manager")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeManager(@PathVariable UUID id, @RequestParam UUID managerId) {
        try {
            return ResponseEntity.ok(service.updateEmployeeManager(id, managerId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    
}