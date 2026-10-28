package org.university.service;

import org.university.dao.CourseDao;
import org.university.dao.EnrollmentDao;
import org.university.dao.ExaminationDao;
import org.university.entity.Course;
import org.university.entity.Examination;

import java.time.LocalDate;
import java.util.List;

public class ExaminationService {

    private final CourseDao courseDAO = new CourseDao();
    private final ExaminationDao examinationDAO =
            new ExaminationDao();

    public void createExamination(
            String title,
            String type,
            LocalDate date,
            double maximumMarks,
            Long courseId) {

        if (title == null || title.isBlank()
                || type == null || type.isBlank()) {
            throw new IllegalArgumentException(
                    "Examination title and type are required."
            );
        }

        if (date == null || maximumMarks <= 0
                || !Double.isFinite(maximumMarks)) {
            throw new IllegalArgumentException(
                    "Enter a valid examination date and maximum marks."
            );
        }

        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new IllegalArgumentException(
                    "Course not found: " + courseId
            );
        }

        Examination examination = new Examination(
                title.trim(),
                type.trim(),
                date,
                maximumMarks,
                course
        );

        examinationDAO.save(examination);

        System.out.println("Examination created successfully.");
    }

    public List<Examination> getAllExaminations() {
        return examinationDAO.findAll();
    }
}