package com.example.EmployeeManager.Util;

public enum role {
    USER,
    ADMIN;

    public String getAuthority () {
        return "ROLE_" + name();
    }
}
 
