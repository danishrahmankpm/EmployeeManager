package com.example.EmployeeManager.Dto.AuthDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    String userId;
    String token;
    
}

