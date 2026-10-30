package com.university.service;

import com.university.dao.EnrollmentDAO;
import com.university.dao.ExaminationDAO;
import com.university.dao.ResultDAO;
import com.university.dao.StudentDAO;
import com.university.entity.Examination;
import com.university.entity.Result;
import com.university.entity.Student;
import com.university.exception.StudentNotFoundException;

import java.util.List;

public class ResultService {

    private final StudentDAO studentDAO = new StudentDAO();
    private final ExaminationDAO examinationDAO = new ExaminationDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final ResultDAO resultDAO = new ResultDAO();

    public Result recordResult(Long studentId, Long examinationId, double marks) {
        Student student = studentDAO.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));
        Examination exam = examinationDAO.findById(examinationId)
                .orElseThrow(() -> new IllegalArgumentException("Examination not found with id: " + examinationId));

        if (!enrollmentDAO.exists(studentId, exam.getCourse().getId())) {
            throw new IllegalStateException("Student " + studentId + " is not enrolled in course "
                    + exam.getCourse().getCode());
        }
        if (marks < 0 || marks > exam.getMaxMarks()) {
            throw new IllegalArgumentException("Marks must be between 0 and " + exam.getMaxMarks());
        }
        if (resultDAO.exists(studentId, examinationId)) {
            throw new IllegalStateException("Result already recorded for this student and exam");
        }
        String grade = calculateGrade(marks, exam.getMaxMarks());
        return resultDAO.save(new Result(student, exam, marks, grade));
    }

    public static String calculateGrade(double marks, int maxMarks) {
        double percentage = (marks * 100.0) / maxMarks;
        if (percentage >= 90) return "A+";
        if (percentage >= 80) return "A";
        if (percentage >= 70) return "B";
        if (percentage >= 60) return "C";
        if (percentage >= 50) return "D";
        return "F";
    }

    public List<Result> getResultsByStudent(Long studentId) {
        return resultDAO.findByStudent(studentId);
    }

    public List<Result> getResultsByExamination(Long examinationId) {
        return resultDAO.findByExamination(examinationId);
    }
}
