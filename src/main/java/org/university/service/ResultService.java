package org.university.service;

import org.university.dao.EnrollmentDao;
import org.university.dao.ExaminationDao;
import org.university.dao.ResultDao;
import org.university.entity.Enrollment;
import org.university.entity.Examination;
import org.university.entity.Result;

import java.util.List;

public class ResultService {

    private static final double PASS_PERCENTAGE = 40.0;

    private final EnrollmentDao enrollmentDAO =
            new EnrollmentDao();

    private final ExaminationDao
            examinationDAO =
            new ExaminationDao();

    private final ResultDao resultDAO = new ResultDao();

    public void recordResult(
            Long enrollmentId,
            Long examinationId,
            double marks) {

        Enrollment enrollment =
                enrollmentDAO.findById(enrollmentId);

        if (enrollment == null) {
            throw new IllegalArgumentException(
                    "Enrollment not found: " + enrollmentId
            );
        }

        Examination examination =
                examinationDAO.findById(examinationId);

        if (examination == null) {
            throw new IllegalArgumentException(
                    "Examination not found: " + examinationId
            );
        }

        if (!enrollment.getCourse().getId()
                .equals(examination.getCourse().getId())) {
            throw new IllegalArgumentException(
                    "The examination does not belong to the student's course."
            );
        }

        if (!Double.isFinite(marks)
                || marks < 0
                || marks > examination.getMaximumMarks()) {
            throw new IllegalArgumentException(
                    "Marks must be between 0 and maximum marks."
            );
        }

        double percentage =
                (marks / examination.getMaximumMarks()) * 100.0;

        boolean passed = percentage >= PASS_PERCENTAGE;

        String grade;

        if (percentage >= 90) {
            grade = "A+";
        } else if (percentage >= 80) {
            grade = "A";
        } else if (percentage >= 70) {
            grade = "B";
        } else if (percentage >= 60) {
            grade = "C";
        } else if (percentage >= 50) {
            grade = "D";
        } else if (passed) {
            grade = "E";
        } else {
            grade = "F";
        }

        Result result = new Result(
                enrollment,
                examination,
                marks,
                grade,
                passed
        );

        resultDAO.save(result);

        System.out.println("Result recorded successfully.");
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.println("Grade: " + grade);
        System.out.println("Status: " + (passed ? "PASS" : "FAIL"));
    }

    public List<Result> getAllResults() {
        return resultDAO.findAll();
    }

    public List<Result> getResultsByStudent(Long studentId) {
        return resultDAO.findByStudent(studentId);
    }
}