package com.example.EmployeeManager.Service;
import java.util.Optional;
import java.util.UUID;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.EmployeeManager.Dto.AuthDto.AuthResponse;
import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Repository.EmployeeRepository;
import com.example.EmployeeManager.Util.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private JwtUtil jwtUtil;  
    @Autowired 
    private AuthenticationManager authManager; 
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public AuthResponse login (String userId, String password) throws AuthenticationException {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(userId, password)
        );
        Optional<Employee> employee=employeeRepository.findById(UUID.fromString(userId));
        String token= jwtUtil.generateToken(UUID.fromString(userId), employee.get().getRole().toString());
        AuthResponse authResponse = new AuthResponse(userId, token);
        return authResponse; 
    }

    

    public void signup(String userId, String password) throws NotFoundException {
        UUID userUUID = UUID.fromString(userId);
        if (!employeeRepository.existsById(userUUID)) {
            throw new NotFoundException();
        }
        employeeRepository.findById(userUUID).ifPresent(employee -> {
            String hashedPassword = passwordEncoder.encode(password); 
            employee.setPassword(hashedPassword);
            employeeRepository.save(employee);
        });
    
    }
}