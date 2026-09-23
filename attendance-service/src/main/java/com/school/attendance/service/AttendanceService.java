package com.school.attendance.service;

import com.school.attendance.client.StudentClient;
import com.school.attendance.entity.Attendance;
import com.school.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {

    private final AttendanceRepository repository;
    private final StudentClient studentClient;

    public AttendanceService(
            AttendanceRepository repository,
            StudentClient studentClient) {

        this.repository = repository;
        this.studentClient = studentClient;
    }

    public Attendance create(Attendance attendance) {
        // Verify that student exists via OpenFeign call
        try {
            studentClient.getStudent(attendance.getStudentId());
        } catch (Exception e) {
            throw new RuntimeException("Student with ID " + attendance.getStudentId() + " not found");
        }

        return repository.save(attendance);
    }

    public Attendance get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));
    }

    public List<Attendance> getByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    public List<Attendance> getByDate(String date) {
        return repository.findByDate(date);
    }

    public Attendance update(Long id, Attendance attendance) {
        Attendance existing = get(id);

        existing.setStudentId(attendance.getStudentId());
        existing.setDate(attendance.getDate());
        existing.setStatus(attendance.getStatus());
        existing.setRemarks(attendance.getRemarks());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.delete(get(id));
    }

    public Map<String, Object> getStudentSummary(Long studentId) {
        List<Attendance> list = repository.findByStudentId(studentId);
        int totalDays = list.size();
        long presentDays = list.stream().filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus())).count();
        long absentDays = list.stream().filter(a -> "ABSENT".equalsIgnoreCase(a.getStatus())).count();
        long lateDays = list.stream().filter(a -> "LATE".equalsIgnoreCase(a.getStatus())).count();
        long halfDays = list.stream().filter(a -> "HALF_DAY".equalsIgnoreCase(a.getStatus())).count();

        double percentage = totalDays > 0 ? ((double) presentDays / totalDays) * 100.0 : 0.0;

        Map<String, Object> summary = new HashMap<>();
        summary.put("studentId", studentId);
        summary.put("totalDays", totalDays);
        summary.put("presentDays", presentDays);
        summary.put("absentDays", absentDays);
        summary.put("lateDays", lateDays);
        summary.put("halfDays", halfDays);
        summary.put("attendancePercentage", Math.round(percentage * 100.0) / 100.0);

        return summary;
    }
}
