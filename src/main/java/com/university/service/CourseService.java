package com.university.service;

import com.university.dao.CourseDAO;
import com.university.dao.FacultyDAO;
import com.university.entity.Course;
import com.university.entity.Faculty;

import java.util.List;

public class CourseService {

    private final CourseDAO courseDAO = new CourseDAO();
    private final FacultyDAO facultyDAO = new FacultyDAO();

    public Faculty addFaculty(String name, String email, String department) {
        return facultyDAO.save(new Faculty(name, email, department));
    }

    public Course addCourse(String code, String title, int credits, int capacity, Long facultyId) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        if (courseDAO.findByCode(code).isPresent()) {
            throw new IllegalStateException("Course with code " + code + " already exists");
        }
        Faculty faculty = facultyDAO.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty not found with id: " + facultyId));
        return courseDAO.save(new Course(code, title, credits, capacity, faculty));
    }

    public Course getCourse(Long id) {
        return courseDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public List<Faculty> getAllFaculty() {
        return facultyDAO.findAll();
    }
}
