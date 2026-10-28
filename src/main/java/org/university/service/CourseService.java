package org.university.service;

import org.university.dao.CourseDao;
import org.university.entity.Course;
import java.util.List;

public class CourseService {

    private final CourseDao dao = new CourseDao();

    public void addCourse(String code, String title,
                          int credits, int capacity) {

        if (code == null || code.isBlank()
                || title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Course code and title are required."
            );
        }

        if (credits <= 0 || capacity <= 0) {
            throw new IllegalArgumentException(
                    "Credits and capacity must be positive."
            );
        }

        dao.save(new Course(
                code.trim(),
                title.trim(),
                credits,
                capacity
        ));
    }

    public List<Course> getAllCourses() {
        return dao.findAll();
    }

    public Course getCourse(Long id) {
        Course course = dao.findById(id);

        if (course == null) {
            throw new IllegalArgumentException(
                    "Course not found: " + id
            );
        }

        return course;
    }
}