package com.example.EmployeeManager.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Repository.EmployeeRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UUID userUUID = UUID.fromString(userId);
        Optional<Employee> employee = employeeRepository.findById(userUUID);

        if (employee.isPresent()) {
            String authority = employee.get().getRole().getAuthority(); 
            System.out.println("Loading user: " + userId);
            System.out.println("Password (hashed): " + employee.get().getPassword());
            System.out.println("Authorities: " + authority);
            return new User(
                employee.get().getId().toString(),
                employee.get().getPassword(),
                List.of(new SimpleGrantedAuthority(authority))  
            );
        } else {
            throw new UsernameNotFoundException("User not found with id: " + userId);
        }
    }

    
}
