package com.university.service;

import com.university.dao.StudentDAO;
import com.university.entity.Student;
import com.university.exception.StudentNotFoundException;

import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO = new StudentDAO();

    public Student registerStudent(String name, String email, String department) {
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new IllegalArgumentException("Student name and email are required");
        }
        if (studentDAO.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Student with email " + email + " already exists");
        }
        return studentDAO.save(new Student(name, email, department));
    }

    public Student getStudent(Long id) {
        return studentDAO.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public void deleteStudent(Long id) {
        getStudent(id); // throws if not found
        studentDAO.delete(id);
    }
}
