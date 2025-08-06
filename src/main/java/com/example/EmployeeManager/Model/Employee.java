package com.example.EmployeeManager.Model;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import com.example.EmployeeManager.Util.role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String password;
    @Enumerated(EnumType.STRING)
    private role role; 
    private String name;
    private LocalDate dob;
    private BigDecimal salary;
    private LocalDate joiningDate;
    private Double yearlyBonusPercent;
    private String address;
    private String title;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
}
