package com.school.student.service;

import com.school.student.entity.Student;
import com.school.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        if (repository.findByRollNumber(student.getRollNumber()).isPresent()) {
            throw new RuntimeException("Roll number already exists");
        }

        return repository.save(student);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudent(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(Long id, Student student) {

        Student existing = getStudent(id);

        existing.setRollNumber(student.getRollNumber());
        existing.setName(student.getName());
        existing.setDateOfBirth(student.getDateOfBirth());
        existing.setGender(student.getGender());
        existing.setEmail(student.getEmail());
        existing.setPhone(student.getPhone());
        existing.setClassName(student.getClassName());
        existing.setSection(student.getSection());
        existing.setAddress(student.getAddress());
        existing.setGuardianName(student.getGuardianName());
        existing.setGuardianPhone(student.getGuardianPhone());
        existing.setAdmissionDate(student.getAdmissionDate());
        existing.setStatus(student.getStatus());

        return repository.save(existing);
    }

    public void deleteStudent(Long id) {
        Student student = getStudent(id);
        repository.delete(student);
    }

    public List<Student> search(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public List<Student> getByClass(String className) {
        return repository.findByClassName(className);
    }
}
