package org.university.service;

import org.university.dao.CourseDao;
import org.university.dao.EnrollmentDao;
import org.university.dao.StudentDao;
import org.university.entity.Course;
import org.university.entity.Enrollment;
import org.university.entity.Student;
import org.university.exception.CourseFullException;
import org.university.exception.DuplicateEnrollmentException;

import java.util.List;

public class EnrollmentService {

    private final StudentDao studentDAO = new StudentDao();
    private final CourseDao courseDAO = new CourseDao();
    private final EnrollmentDao enrollmentDAO = new EnrollmentDao();

    public void enroll(Long studentId, Long courseId) {

        // Find student using StudentDao object
        Student student = studentDAO.findById(studentId);

        if (student == null) {
            throw new IllegalArgumentException(
                    "Student not found: " + studentId
            );
        }

        // Find course using CourseDao object
        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new IllegalArgumentException(
                    "Course not found: " + courseId
            );
        }

        // Check duplicate enrollment
        if (enrollmentDAO.exists(studentId, courseId)) {
            throw new DuplicateEnrollmentException(
                    "Student is already enrolled in this course."
            );
        }

        // Check course capacity
        long enrolled =
                enrollmentDAO.countActiveByCourse(courseId);

        if (enrolled >= course.getCapacity()) {
            throw new CourseFullException(
                    "The course has reached its enrollment limit."
            );
        }

        // Save enrollment
        enrollmentDAO.save(new Enrollment(student, course));

        System.out.println("Enrollment completed successfully.");
    }

    // Display all enrollments
    public List<Enrollment> getAllEnrollments() {
        return enrollmentDAO.findAll();
    }
}

