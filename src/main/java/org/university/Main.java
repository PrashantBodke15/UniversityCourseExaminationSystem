package org.university;

import org.university.entity.*;
import org.university.report.AcademicReport;
import org.university.service.*;
import org.university.util.JPAUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final StudentService studentService =
            new StudentService();

    private static final CourseService courseService =
            new CourseService();

    private static final EnrollmentService enrollmentService =
            new EnrollmentService();

    private static final ExaminationService examinationService =
            new ExaminationService();

    private static final ResultService resultService =
            new ResultService();

    private static final AcademicReport reports =
            new AcademicReport();

    public static void main(String[] args) {

        try {
            runApplication();
        } catch (Exception e) {
            System.err.println("Application error: " + e.getMessage());
        } finally {
            sc.close();
            JPAUtil.close();
        }
    }

    private static void runApplication() {

        boolean running = true;

        while (running) {
            try {
                displayMenu();

                int choice = readInt("Enter your choice: ");

                switch (choice) {
                    case 1 -> registerStudent();
                    case 2 -> listStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 6 -> addCourse();
                    case 7 -> listCourses();
                    case 8 -> registerFaculty();
                    case 9 -> listFaculty();
                    case 10 -> enrollStudent();
                    case 11 -> listEnrollments();
                    case 12 -> createExamination();
                    case 13 -> listExaminations();
                    case 14 -> recordResult();
                    case 15 -> listResults();
                    case 16 -> displayCourseReport();
                    case 17 -> displayPerformanceReport();
                    case 0 -> {
                        running = false;
                        System.out.println("Thank you for using the application.");
                    }
                    default -> System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n==============================================");
        System.out.println(" UNIVERSITY COURSE AND EXAMINATION SYSTEM");
        System.out.println("==============================================");
        System.out.println("1.  Register Student");
        System.out.println("2.  List Students");
        System.out.println("3.  Search Student");
        System.out.println("4.  Update Student");
        System.out.println("5.  Delete Student");
        System.out.println("6.  Add Course");
        System.out.println("7.  List Courses");
        System.out.println("8.  Register Faculty");
        System.out.println("9.  List Faculty");
        System.out.println("10. Enroll Student");
        System.out.println("11. List Enrollments");
        System.out.println("12. Create Examination");
        System.out.println("13. List Examinations");
        System.out.println("14. Record Result");
        System.out.println("15. List Results");
        System.out.println("16. Course Enrollment Report");
        System.out.println("17. Student Performance Report");
        System.out.println("0.  Exit");
        System.out.println("==============================================");
    }

    private static void registerStudent() {
        String name = readString("Student name: ");
        String email = readString("Email: ");
        String phone = readString("Phone: ");
        String department = readString("Department: ");

        studentService.registerStudent(
                name, email, phone, department
        );

        System.out.println("Student registered successfully.");
    }

    private static void listStudents() {
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        students.forEach(System.out::println);
    }

    private static void searchStudent() {
        long id = readLong("Enter student ID: ");

        System.out.println(
                studentService.getStudent(id)
        );
    }

    private static void updateStudent() {
        long id = readLong("Enter student ID: ");

        String name = readString("New name: ");
        String email = readString("New email: ");
        String phone = readString("New phone: ");
        String department = readString("New department: ");

        studentService.updateStudent(
                id, name, email, phone, department
        );

        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        long id = readLong("Enter student ID: ");

        studentService.deleteStudent(id);

        System.out.println("Student deleted successfully.");
    }

    private static void addCourse() {
        String code = readString("Course code: ");
        String title = readString("Course title: ");
        int credits = readInt("Credits: ");
        int capacity = readInt("Maximum enrollment capacity: ");

        courseService.addCourse(
                code, title, credits, capacity
        );

        System.out.println("Course added successfully.");
    }

    private static void listCourses() {
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        courses.forEach(System.out::println);
    }

    private static void registerFaculty() {
        String name = readString("Faculty name: ");
        String email = readString("Faculty email: ");
        String specialization = readString("Specialization: ");

        Faculty faculty = new Faculty(
                name, email, specialization
        );

        org.university.dao.FacultyDao dao =
                new org.university.dao.FacultyDao();

        dao.save(faculty);

        System.out.println("Faculty registered successfully.");
    }

    private static void listFaculty() {
        org.university.dao.FacultyDao dao =
                new org.university.dao.FacultyDao();

        List<Faculty> faculty = dao.findAll();

        if (faculty.isEmpty()) {
            System.out.println("No faculty found.");
            return;
        }

        faculty.forEach(System.out::println);
    }

    private static void enrollStudent() {
        long studentId = readLong("Student ID: ");
        long courseId = readLong("Course ID: ");

        enrollmentService.enroll(studentId, courseId);
    }

    private static void listEnrollments() {
        List<Enrollment> enrollments =
                enrollmentService.getAllEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        enrollments.forEach(System.out::println);
    }

    private static void createExamination() {
        String title = readString("Examination title: ");
        String type = readString("Examination type: ");

        LocalDate date = LocalDate.parse(
                readString("Date (YYYY-MM-DD): ")
        );

        double maximumMarks =
                readDouble("Maximum marks: ");

        long courseId = readLong("Course ID: ");

        examinationService.createExamination(
                title, type, date, maximumMarks, courseId
        );
    }

    private static void listExaminations() {
        List<Examination> examinations =
                examinationService.getAllExaminations();

        if (examinations.isEmpty()) {
            System.out.println("No examinations found.");
            return;
        }

        examinations.forEach(System.out::println);
    }

    private static void recordResult() {
        long enrollmentId = readLong("Enrollment ID: ");
        long examinationId = readLong("Examination ID: ");
        double marks = readDouble("Marks obtained: ");

        resultService.recordResult(
                enrollmentId, examinationId, marks
        );
    }

    private static void listResults() {
        List<Result> results = resultService.getAllResults();

        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }

        results.forEach(System.out::println);
    }

    private static void displayCourseReport() {
        reports.displayCourseEnrollmentReport();
    }

    private static void displayPerformanceReport() {
        reports.displayStudentPerformanceReport();
    }

    private static String readString(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readString(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid integer.");
            }
        }
    }

    private static long readLong(String message) {
        while (true) {
            try {
                return Long.parseLong(readString(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid ID.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                return Double.parseDouble(readString(message));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}