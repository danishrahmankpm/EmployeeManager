package com.example.EmployeeManager.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.EmployeeManager.Model.Department;
import com.example.EmployeeManager.Model.Employee;

import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    
    Optional<Department> findById(UUID id);
    
    
}
