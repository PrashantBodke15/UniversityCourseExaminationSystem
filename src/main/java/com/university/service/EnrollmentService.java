package com.university.service;

import com.university.dao.CourseDAO;
import com.university.dao.EnrollmentDAO;
import com.university.dao.StudentDAO;
import com.university.entity.Course;
import com.university.entity.Enrollment;
import com.university.entity.Student;
import com.university.exception.CourseFullException;
import com.university.exception.DuplicateEnrollmentException;
import com.university.exception.StudentNotFoundException;

import java.time.LocalDate;
import java.util.List;

public class EnrollmentService {

    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public Enrollment enroll(Long studentId, Long courseId) {
        Student student = studentDAO.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        if (enrollmentDAO.exists(studentId, courseId)) {
            throw new DuplicateEnrollmentException(studentId, course.getCode());
        }
        if (enrollmentDAO.countByCourse(courseId) >= course.getCapacity()) {
            throw new CourseFullException(course.getCode());
        }
        return enrollmentDAO.save(new Enrollment(student, course, LocalDate.now()));
    }

    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentDAO.findByStudent(studentId);
    }

    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentDAO.findByCourse(courseId);
    }

    public long countEnrollments(Long courseId) {
        return enrollmentDAO.countByCourse(courseId);
    }
}
