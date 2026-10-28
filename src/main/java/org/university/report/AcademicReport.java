package org.university.report;

import org.university.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AcademicReport {

    public void displayCourseEnrollmentReport() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            List<Object[]> rows = em.createQuery(
                    "SELECT c.code, c.title, c.capacity, " +
                            "COUNT(e.id) " +
                            "FROM Course c LEFT JOIN c.enrollments e " +
                            "ON e.status = 'ACTIVE' " +
                            "GROUP BY c.id, c.code, c.title, c.capacity " +
                            "ORDER BY c.code",
                    Object[].class
            ).getResultList();

            System.out.println("\n========== COURSE ENROLLMENT REPORT ==========");

            for (Object[] row : rows) {
                System.out.println("Course Code : " + row[0]);
                System.out.println("Course Title: " + row[1]);
                System.out.println("Capacity    : " + row[2]);
                System.out.println("Enrolled    : " + row[3]);
                System.out.println("----------------------------------------------");
            }
        } finally {
            em.close();
        }
    }

    public void displayStudentPerformanceReport() {

        EntityManager em = JPAUtil.getEntityManager();

        try {
            List<Object[]> rows = em.createQuery(
                    "SELECT s.id, s.name, " +
                            "AVG(r.marksObtained * 100.0 / " +
                            "r.examination.maximumMarks) " +
                            "FROM Result r " +
                            "JOIN r.enrollment e " +
                            "JOIN e.student s " +
                            "GROUP BY s.id, s.name " +
                            "ORDER BY AVG(r.marksObtained * 100.0 / " +
                            "r.examination.maximumMarks) DESC",
                    Object[].class
            ).getResultList();

            System.out.println("\n========== STUDENT PERFORMANCE REPORT ==========");

            for (Object[] row : rows) {
                System.out.println(
                        "Student ID: " + row[0]
                                + " | Name: " + row[1]
                                + " | Average: "
                                + String.format("%.2f%%", row[2])
                );
            }
        } finally {
            em.close();
        }
    }
}