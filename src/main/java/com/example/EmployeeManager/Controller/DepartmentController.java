package com.example.EmployeeManager.Controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.EmployeeManager.Dto.DepartmentDto;
import com.example.EmployeeManager.Dto.EmployeeDto;
import com.example.EmployeeManager.Model.Department;
import com.example.EmployeeManager.Service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<Department> createDepartment(@RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.ok(departmentService.createDepartment(departmentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/{id}/update")
    public ResponseEntity<Department> updateDepartment(@PathVariable UUID id, @RequestBody DepartmentDto departmentDto) {
        try {
            return ResponseEntity.ok(departmentService.updateDepartment(id, departmentDto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(departmentService.getDepartmentById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/lookup")
    public ResponseEntity<Page<EmployeeDto>> getEmployeesByDepartment(@PathVariable UUID id, @PageableDefault(page = 0, size = 20) Pageable pageable) {
        try {
            return ResponseEntity.ok(departmentService.getEmployeesByDepartment(id, pageable));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DepartmentDto>> getAll(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseEntity.ok(departmentService.getAll(pageable));
    }
}
