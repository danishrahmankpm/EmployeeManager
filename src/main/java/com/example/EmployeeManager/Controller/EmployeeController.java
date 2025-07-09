package com.example.EmployeeManager.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.EmployeeManager.Dto.EmployeeDto;
import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired private EmployeeService service;

    @PostMapping("/create")
    public ResponseEntity<Employee> create(@RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update (@PathVariable UUID id, @RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/department")
    public ResponseEntity<Void> changeDepartment(@PathVariable UUID id, @RequestParam UUID departmentId) {
        service.changeDepartment(id, departmentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/lookup")
    public ResponseEntity<List<EmployeeNameIdDto>> lookup() {
        return ResponseEntity.ok(service.getAllNameAndId());
    }
}