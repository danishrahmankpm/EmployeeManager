package com.example.EmployeeManager.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeManager.Model.Attendance;
import com.example.EmployeeManager.Model.Employee;
import com.example.EmployeeManager.Repository.AttendanceRepository;
import com.example.EmployeeManager.Repository.EmployeeRepository;



@Service
public class AttendanceService {
    @Autowired
    private  AttendanceRepository attendanceRepository;
    @Autowired
    private  EmployeeRepository employeeRepository;

    
    public Attendance checkIn(UUID employeeId) throws RuntimeException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        // Prevent duplicate check-in
        attendanceRepository.findByEmployeeIdAndAttendanceDate(employee.getId(), today)
                .ifPresent(a -> {
                    throw new RuntimeException("Employee already checked in today");
                });

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalTime.now());
        attendance.setStatus("Present");
        attendance.setPresent(true); // Set isPresent to true on check-in

        return attendanceRepository.save(attendance);
    }

    /**
     * Records check-out time for the given employee.
     */
    public Attendance checkOut(UUID employeeId) throws RuntimeException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepository.findByEmployeeIdAndAttendanceDate(employee.getId(),today)
                .orElseThrow(() -> new RuntimeException("Check-in not found for today"));

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Employee already checked out");
        }

        attendance.setCheckOutTime(LocalTime.now());

        return attendanceRepository.save(attendance);
    }
    public void markLeave(UUID employeeId) throws RuntimeException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        LocalDate today = LocalDate.now();

        if( attendanceRepository.findByEmployeeIdAndAttendanceDate(employee.getId(), today).isPresent()) {
            throw new RuntimeException("Record present");
        }

         Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(today);
        attendance.setCheckInTime(LocalTime.now());
        attendance.setStatus("Leave");
        attendance.setPresent(false); 

        attendanceRepository.save(attendance);
    }

    /**
     * Get attendance logs for a specific employee and date range.
     */
    public List<Attendance> getAttendanceForEmployee (UUID employeeId, LocalDate from, LocalDate to) {
        if(employeeRepository.findById(employeeId).isEmpty()) {
            throw new RuntimeException("Employee not found");
        }
        return attendanceRepository.findByEmployeeIdAndAttendanceDateBetween(employeeId, from, to);
    }
}

