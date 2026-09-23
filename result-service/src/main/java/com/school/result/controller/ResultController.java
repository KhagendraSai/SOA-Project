package com.school.result.controller;

import com.school.result.entity.Result;
import com.school.result.service.ResultService;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result create(@RequestBody Result result) {
        return service.create(result);
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/student/{studentId}")
    public List<Result> getByStudent(@PathVariable Long studentId) {
        return service.getByStudent(studentId);
    }

    @GetMapping("/student/{studentId}/summary")
    public Map<String, Object> getStudentSummary(@PathVariable Long studentId) {
        return service.getStudentSummary(studentId);
    }

    @PutMapping("/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody Result result) {
        return service.update(id, result);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Result deleted successfully";
    }
}
