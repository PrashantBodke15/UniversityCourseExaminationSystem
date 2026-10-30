package com.university;

import com.university.entity.Course;
import com.university.entity.Examination;
import com.university.entity.Faculty;
import com.university.entity.Student;
import com.university.report.AcademicReport;
import com.university.service.CourseService;
import com.university.service.EnrollmentService;
import com.university.service.ExaminationService;
import com.university.service.ResultService;
import com.university.service.StudentService;
import com.university.util.JPAUtil;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();
        ExaminationService examinationService = new ExaminationService();
        ResultService resultService = new ResultService();
        AcademicReport report = new AcademicReport();

        try {
            // 1. Faculty + Courses
            Faculty f1 = courseService.addFaculty("Dr. Patil", "patil@uni.edu", "Computer Science");
            Faculty f2 = courseService.addFaculty("Dr. Kulkarni", "kulkarni@uni.edu", "Mathematics");

            Course java = courseService.addCourse("CS101", "Java Programming", 4, 2, f1.getId());
            Course math = courseService.addCourse("MA101", "Discrete Mathematics", 3, 5, f2.getId());
            System.out.println("Created: " + java);
            System.out.println("Created: " + math);

            // 2. Students
            Student s1 = studentService.registerStudent("Amit Deshmukh", "amit@uni.edu", "Computer Science");
            Student s2 = studentService.registerStudent("Sneha Joshi", "sneha@uni.edu", "Computer Science");
            Student s3 = studentService.registerStudent("Rahul More", "rahul@uni.edu", "Mathematics");
            System.out.println("Created: " + s1);
            System.out.println("Created: " + s2);
            System.out.println("Created: " + s3);

            // 3. Enrollments
            enrollmentService.enroll(s1.getId(), java.getId());
            enrollmentService.enroll(s2.getId(), java.getId());
            enrollmentService.enroll(s1.getId(), math.getId());
            System.out.println("\nEnrollments done.");

            // 4. Custom exceptions demo
            try {
                enrollmentService.enroll(s1.getId(), java.getId());
            } catch (RuntimeException e) {
                System.out.println("Expected error (duplicate): " + e.getMessage());
            }
            try {
                enrollmentService.enroll(s3.getId(), java.getId());
            } catch (RuntimeException e) {
                System.out.println("Expected error (course full): " + e.getMessage());
            }
            try {
                studentService.getStudent(999L);
            } catch (RuntimeException e) {
                System.out.println("Expected error (not found): " + e.getMessage());
            }

            // 5. Examinations
            Examination e1 = examinationService.scheduleExam(java.getId(), LocalDate.now().plusDays(30), 100, "Hall A");
            Examination e2 = examinationService.scheduleExam(math.getId(), LocalDate.now().plusDays(35), 50, "Hall B");
            System.out.println("\nScheduled: " + e1);
            System.out.println("Scheduled: " + e2);

            // 6. Results
            System.out.println("\nSaved: " + resultService.recordResult(s1.getId(), e1.getId(), 88));
            System.out.println("Saved: " + resultService.recordResult(s2.getId(), e1.getId(), 67));
            System.out.println("Saved: " + resultService.recordResult(s1.getId(), e2.getId(), 41));

            // 7. Reports
            report.printStudentReport(s1.getId());
            report.printStudentReport(s2.getId());
            report.printCourseReport(java.getId());
            report.printCourseReport(math.getId());

        } catch (RuntimeException ex) {
            System.err.println("Unexpected error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            JPAUtil.shutdown();
        }
    }
}
