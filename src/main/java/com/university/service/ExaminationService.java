package com.university.service;

import com.university.dao.CourseDAO;
import com.university.dao.ExaminationDAO;
import com.university.entity.Course;
import com.university.entity.Examination;

import java.time.LocalDate;
import java.util.List;

public class ExaminationService {

    private final CourseDAO courseDAO = new CourseDAO();
    private final ExaminationDAO examinationDAO = new ExaminationDAO();

    public Examination scheduleExam(Long courseId, LocalDate date, int maxMarks, String venue) {
        if (maxMarks <= 0) {
            throw new IllegalArgumentException("Max marks must be greater than 0");
        }
        Course course = courseDAO.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));
        return examinationDAO.save(new Examination(course, date, maxMarks, venue));
    }

    public Examination getExam(Long id) {
        return examinationDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Examination not found with id: " + id));
    }

    public List<Examination> getExamsByCourse(Long courseId) {
        return examinationDAO.findByCourse(courseId);
    }

    public List<Examination> getAllExams() {
        return examinationDAO.findAll();
    }
}
