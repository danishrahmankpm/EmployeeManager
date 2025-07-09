package com.example.EmployeeManager.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EmployeeManager.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByDepartmentId(UUID departmentId);
    List<Employee> findByTitle(String title);
    Optional<Employee> findByName(String name);
}
