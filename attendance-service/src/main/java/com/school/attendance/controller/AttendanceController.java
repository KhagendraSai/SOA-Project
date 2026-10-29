package com.school.attendance.controller;

import com.school.attendance.entity.Attendance;
import com.school.attendance.service.AttendanceService;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    // TEACHER: Create attendance
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    public Attendance create(@RequestBody Attendance attendance) {
        return service.create(attendance);
    }

    // TEACHER: Get attendance by attendance ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public Attendance get(@PathVariable Long id) {
        return service.get(id);
    }

    // TEACHER: Get attendance records for a student
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public List<Attendance> getByStudent(
            @PathVariable Long studentId) {
        return service.getByStudent(studentId);
    }

    // TEACHER: Get attendance summary for a student
    @GetMapping("/student/{studentId}/summary")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public Map<String, Object> getStudentSummary(
            @PathVariable Long studentId) {
        return service.getStudentSummary(studentId);
    }

    // TEACHER: Get attendance by date
    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('TEACHER','STUDENT')")
    public List<Attendance> getByDate(
            @PathVariable String date) {
        return service.getByDate(date);
    }

    // TEACHER: Update attendance
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public Attendance update(
            @PathVariable Long id,
            @RequestBody Attendance attendance) {
        return service.update(id, attendance);
    }

    // TEACHER: Delete attendance
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Attendance deleted successfully";
    }
}