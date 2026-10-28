package org.university.dao;

import org.university.entity.Result;
import org.university.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ResultDao {

    public void save(Result result) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(result);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Result> findAll() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT r FROM Result r ORDER BY r.id",
                    Result.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Result> findByStudent(Long studentId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                            "SELECT r FROM Result r " +
                                    "WHERE r.enrollment.student.id = :studentId " +
                                    "ORDER BY r.id",
                            Result.class
                    )
                    .setParameter("studentId", studentId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}