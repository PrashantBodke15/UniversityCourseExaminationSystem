package com.university.report;

import com.university.entity.Course;
import com.university.entity.Enrollment;
import com.university.entity.Examination;
import com.university.entity.Result;
import com.university.entity.Student;
import com.university.service.CourseService;
import com.university.service.EnrollmentService;
import com.university.service.ExaminationService;
import com.university.service.ResultService;
import com.university.service.StudentService;

import java.util.List;

public class AcademicReport {

    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final EnrollmentService enrollmentService = new EnrollmentService();
    private final ExaminationService examinationService = new ExaminationService();
    private final ResultService resultService = new ResultService();

    public void printStudentReport(Long studentId) {
        Student student = studentService.getStudent(studentId);
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        List<Result> results = resultService.getResultsByStudent(studentId);

        System.out.println("\n================ STUDENT REPORT ================");
        System.out.println("Name       : " + student.getName());
        System.out.println("Email      : " + student.getEmail());
        System.out.println("Department : " + student.getDepartment());

        System.out.println("\nEnrolled Courses:");
        if (enrollments.isEmpty()) {
            System.out.println("  (none)");
        }
        for (Enrollment e : enrollments) {
            System.out.printf("  %-8s %-30s credits=%d%n",
                    e.getCourse().getCode(), e.getCourse().getTitle(), e.getCourse().getCredits());
        }

        System.out.println("\nResults:");
        if (results.isEmpty()) {
            System.out.println("  (no results yet)");
        }
        double totalPercentage = 0;
        for (Result r : results) {
            int max = r.getExamination().getMaxMarks();
            double percentage = (r.getMarksObtained() * 100.0) / max;
            totalPercentage += percentage;
            System.out.printf("  %-8s %6.1f / %-4d  %6.2f%%  Grade: %s%n",
                    r.getExamination().getCourse().getCode(), r.getMarksObtained(), max,
                    percentage, r.getGrade());
        }
        if (!results.isEmpty()) {
            System.out.printf("%nAverage Percentage: %.2f%%%n", totalPercentage / results.size());
        }
        System.out.println("================================================");
    }

    public void printCourseReport(Long courseId) {
        Course course = courseService.getCourse(courseId);
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        List<Examination> exams = examinationService.getExamsByCourse(courseId);

        System.out.println("\n================ COURSE REPORT =================");
        System.out.println("Course  : " + course.getCode() + " - " + course.getTitle());
        System.out.println("Faculty : " + (course.getFaculty() != null ? course.getFaculty().getName() : "N/A"));
        System.out.println("Seats   : " + enrollments.size() + " / " + course.getCapacity());

        System.out.println("\nEnrolled Students:");
        if (enrollments.isEmpty()) {
            System.out.println("  (none)");
        }
        for (Enrollment e : enrollments) {
            System.out.println("  " + e.getStudent().getName() + " (" + e.getStudent().getEmail() + ")");
        }

        System.out.println("\nExaminations:");
        if (exams.isEmpty()) {
            System.out.println("  (none)");
        }
        for (Examination x : exams) {
            List<Result> results = resultService.getResultsByExamination(x.getId());
            double avg = results.stream().mapToDouble(Result::getMarksObtained).average().orElse(0);
            System.out.printf("  Exam #%d on %s at %s | max=%d | results=%d | avg marks=%.2f%n",
                    x.getId(), x.getExamDate(), x.getVenue(), x.getMaxMarks(), results.size(), avg);
        }
        System.out.println("================================================");
    }
}
