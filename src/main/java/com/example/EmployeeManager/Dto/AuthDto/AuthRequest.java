package com.example.EmployeeManager.Dto.AuthDto;

import lombok.Data;

@Data
public class AuthRequest {
    String userId;
    String password;
}
