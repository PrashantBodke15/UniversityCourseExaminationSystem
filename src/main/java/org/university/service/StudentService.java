package org.university.service;

import org.university.dao.StudentDao;
import org.university.entity.Student;
import org.university.exception.StudentNotFoundException;

import java.util.List;

public class StudentService {

    private final StudentDao dao = new StudentDao();

    public void registerStudent(String name, String email,
                                String phone, String department) {

        if (name == null || name.isBlank()
                || email == null || email.isBlank()
                || department == null || department.isBlank()) {
            throw new IllegalArgumentException(
                    "Name, email and department are required."
            );
        }

        if (dao.findByEmail(email.trim()) != null) {
            throw new IllegalArgumentException(
                    "A student with this email already exists."
            );
        }

        dao.save(new Student(
                name.trim(),
                email.trim(),
                phone == null ? "" : phone.trim(),
                department.trim()
        ));
    }

    public List<Student> getAllStudents() {
        return dao.findAll();
    }

    public Student getStudent(Long id) {
        Student student = dao.findById(id);

        if (student == null) {
            throw new StudentNotFoundException(
                    "Student not found: " + id
            );
        }

        return student;
    }

    public void updateStudent(Long id, String name,
                              String email, String phone,
                              String department) {

        Student student = getStudent(id);

        Student existing = dao.findByEmail(email.trim());

        if (existing != null && !existing.getId().equals(id)) {
            throw new IllegalArgumentException(
                    "Another student already uses this email."
            );
        }

        student.setName(name.trim());
        student.setEmail(email.trim());
        student.setPhone(phone == null ? "" : phone.trim());
        student.setDepartment(department.trim());

        dao.update(student);
    }

    public void deleteStudent(Long id) {
        getStudent(id);
        dao.delete(id);
    }
}