package com.example.EmployeeManager.Controller;

import com.example.EmployeeManager.Model.Attendance;
import com.example.EmployeeManager.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/{employeeId}/checkin")
    public ResponseEntity<Attendance> checkIn(@PathVariable UUID employeeId) {
        try {
            Attendance attendance = attendanceService.checkIn(employeeId);
            return ResponseEntity.ok(attendance);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{employeeId}/checkout")
    public ResponseEntity<Attendance> checkOut(@PathVariable UUID employeeId) {
        try {
            Attendance attendance = attendanceService.checkOut(employeeId);
            return ResponseEntity.ok(attendance);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{employeeId}/mark-leave")
    public ResponseEntity<Void> markLeave(@PathVariable UUID employeeId) {
        try {
            attendanceService.markLeave(employeeId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<Attendance>> getAttendanceForEmployee(
            @PathVariable UUID employeeId,
            @RequestParam("from") String fromDate,
            @RequestParam("to") String toDate
    ) {
        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);

        try {
            List<Attendance> attendances = attendanceService.getAttendanceForEmployee(employeeId, from, to);
            return ResponseEntity.ok(attendances);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
