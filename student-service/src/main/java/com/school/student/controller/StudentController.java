package com.school.student.controller;

import com.school.student.entity.Student;
import com.school.student.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // TEACHER: Create a student
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    public Student create(@Valid @RequestBody Student student) {
        return service.createStudent(student);
    }

    // TEACHER: View all students
    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    public List<Student> getAll() {
        return service.getAllStudents();
    }

    // TEACHER: View a student by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public Student get(@PathVariable Long id) {
        return service.getStudent(id);
    }

    // TEACHER: Update a student
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public Student update(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {

        return service.updateStudent(id, student);
    }

    // TEACHER: Delete a student
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String delete(@PathVariable Long id) {
        service.deleteStudent(id);
        return "Student deleted successfully";
    }

    // TEACHER: Search students by name
    @GetMapping("/search")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Student> search(@RequestParam String name) {
        return service.search(name);
    }

    // TEACHER: View students by class
    @GetMapping("/class/{className}")
    @PreAuthorize("hasRole('TEACHER')")
    public List<Student> getByClass(
            @PathVariable String className) {

        return service.getByClass(className);
    }
}