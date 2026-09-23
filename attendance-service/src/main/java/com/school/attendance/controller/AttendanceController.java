package com.school.attendance.controller;

import com.school.attendance.entity.Attendance;
import com.school.attendance.service.AttendanceService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Attendance create(@RequestBody Attendance attendance) {
        return service.create(attendance);
    }

    @GetMapping("/{id}")
    public Attendance get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Attendance> getByStudent(@PathVariable Long studentId) {
        return service.getByStudent(studentId);
    }

    @GetMapping("/student/{studentId}/summary")
    public Map<String, Object> getStudentSummary(@PathVariable Long studentId) {
        return service.getStudentSummary(studentId);
    }

    @GetMapping("/date/{date}")
    public List<Attendance> getByDate(@PathVariable String date) {
        return service.getByDate(date);
    }

    @PutMapping("/{id}")
    public Attendance update(
            @PathVariable Long id,
            @RequestBody Attendance attendance) {
        return service.update(id, attendance);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Attendance deleted successfully";
    }
}
