package org.university.dao;

import org.university.entity.Enrollment;
import org.university.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class EnrollmentDao {

    public void save(Enrollment enrollment) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(enrollment);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Enrollment> findAll() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Enrollment e ORDER BY e.id",
                    Enrollment.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Enrollment findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Enrollment.class, id);
        } finally {
            em.close();
        }
    }

    public boolean exists(Long studentId, Long courseId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            Long count = em.createQuery(
                            "SELECT COUNT(e) FROM Enrollment e " +
                                    "WHERE e.student.id = :studentId " +
                                    "AND e.course.id = :courseId " +
                                    "AND e.status = 'ACTIVE'",
                            Long.class
                    )
                    .setParameter("studentId", studentId)
                    .setParameter("courseId", courseId)
                    .getSingleResult();

            return count > 0;
        } finally {
            em.close();
        }
    }

    public long countActiveByCourse(Long courseId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT COUNT(e) FROM Enrollment e " +
                                    "WHERE e.course.id = :courseId " +
                                    "AND e.status = 'ACTIVE'",
                            Long.class
                    )
                    .setParameter("courseId", courseId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}