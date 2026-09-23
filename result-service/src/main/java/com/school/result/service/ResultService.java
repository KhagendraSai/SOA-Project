package com.school.result.service;

import com.school.result.client.StudentClient;
import com.school.result.entity.Result;
import com.school.result.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResultService {

    private final ResultRepository repository;
    private final StudentClient studentClient;

    public ResultService(
            ResultRepository repository,
            StudentClient studentClient) {

        this.repository = repository;
        this.studentClient = studentClient;
    }

    public Result create(Result result) {
        // Verify student existence using OpenFeign
        try {
            studentClient.getStudent(result.getStudentId());
        } catch (Exception e) {
            throw new RuntimeException("Student with ID " + result.getStudentId() + " not found");
        }

        validateMarks(result);

        result.setGrade(calculateGrade(result.getMarks(), result.getMaxMarks()));

        return repository.save(result);
    }

    private void validateMarks(Result result) {
        if (result.getMarks() == null || result.getMaxMarks() == null) {
            throw new RuntimeException("Marks and Max Marks cannot be null");
        }
        if (result.getMarks() < 0 || result.getMarks() > result.getMaxMarks() || result.getMaxMarks() <= 0) {
            throw new RuntimeException("Invalid marks: Marks must be between 0 and Max Marks");
        }
    }

    public String calculateGrade(Double marks, Double maxMarks) {
        if (maxMarks <= 0) return "F";
        double percentage = (marks / maxMarks) * 100.0;

        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B+";
        if (percentage >= 60) return "B";
        if (percentage >= 50) return "C";
        if (percentage >= 40) return "D";

        return "F";
    }

    public Result get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + id));
    }

    public List<Result> getByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public Result update(Long id, Result result) {
        Result existing = get(id);

        validateMarks(result);

        existing.setStudentId(result.getStudentId());
        existing.setSubject(result.getSubject());
        existing.setExamType(result.getExamType());
        existing.setMarks(result.getMarks());
        existing.setMaxMarks(result.getMaxMarks());
        existing.setAcademicYear(result.getAcademicYear());
        existing.setSemester(result.getSemester());
        existing.setRemarks(result.getRemarks());

        existing.setGrade(calculateGrade(result.getMarks(), result.getMaxMarks()));

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.delete(get(id));
    }

    public Map<String, Object> getStudentSummary(Long studentId) {
        List<Result> results = repository.findByStudentId(studentId);
        int totalSubjects = results.size();
        double totalMarksObtained = results.stream().mapToDouble(Result::getMarks).sum();
        double totalMaxMarks = results.stream().mapToDouble(Result::getMaxMarks).sum();
        double overallPercentage = totalMaxMarks > 0 ? (totalMarksObtained / totalMaxMarks) * 100.0 : 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("studentId", studentId);
        summary.put("totalSubjects", totalSubjects);
        summary.put("totalMarksObtained", totalMarksObtained);
        summary.put("totalMaxMarks", totalMaxMarks);
        summary.put("overallPercentage", Math.round(overallPercentage * 100.0) / 100.0);
        summary.put("overallGrade", calculateGrade(totalMarksObtained, totalMaxMarks > 0 ? totalMaxMarks : 100.0));
        summary.put("results", results);

        return summary;
    }
}
