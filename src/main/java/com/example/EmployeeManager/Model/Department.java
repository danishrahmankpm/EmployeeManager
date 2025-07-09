package com.example.EmployeeManager.Model;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "department")
@Data
public class Department {
    @Id
    @GeneratedValue(generator = "UUID")
    
    private UUID id;

    private String name;
    private LocalDate creationDate;

    @OneToOne
    @JoinColumn(name = "head_id")
    private Employee head;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}