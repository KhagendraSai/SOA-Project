package com.school.result.controller;

import com.school.result.entity.Result;
import com.school.result.service.ResultService;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService service;

    public ResultController(ResultService service) {
        this.service = service;
    }

    // TEACHER: Create a result
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    public Result create(@RequestBody Result result) {
        return service.create(result);
    }

    // TEACHER and STUDENT: Get result by result ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public Result get(@PathVariable Long id) {
        return service.get(id);
    }

    // TEACHER and STUDENT: Get results for a student
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public List<Result> getByStudent(@PathVariable Long studentId) {
        return service.getByStudent(studentId);
    }

    // TEACHER and STUDENT: Get a student's result summary
    @GetMapping("/student/{studentId}/summary")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public Map<String, Object> getStudentSummary(
            @PathVariable Long studentId) {
        return service.getStudentSummary(studentId);
    }

    // TEACHER: Update a result
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public Result update(
            @PathVariable Long id,
            @RequestBody Result result) {
        return service.update(id, result);
    }

    // TEACHER: Delete a result
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Result deleted successfully";
    }
}