
package org.university.dao;

import org.university.entity.Student;
import org.university.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentDao {

    // Find student by email
    public Student findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s WHERE s.email = :email",
                    Student.class
            );

            query.setParameter("email", email);

            List<Student> students = query.getResultList();

            return students.isEmpty() ? null : students.get(0);

        } finally {
            em.close();
        }
    }

    // Find student by ID
    public Student findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    // Get all students
    public List<Student> findAll() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            TypedQuery<Student> query = em.createQuery(
                    "SELECT s FROM Student s ORDER BY s.id",
                    Student.class
            );

            return query.getResultList();

        } finally {
            em.close();
        }
    }

    // Save a new student
    public Student save(Student student) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(student);
            transaction.commit();

            return student;

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    // Update an existing student
    public Student update(Student student) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Student updatedStudent = em.merge(student);

            transaction.commit();

            return updatedStudent;

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    // Delete student by ID
    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Student student = em.find(Student.class, id);

            if (student != null) {
                em.remove(student);
            }

            transaction.commit();

        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }
}

