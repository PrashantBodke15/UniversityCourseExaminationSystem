package org.university.dao;

import org.university.entity.Examination;
import org.university.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ExaminationDao{

    public void save(Examination examination) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(examination);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Examination> findAll() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT x FROM Examination x ORDER BY x.id",
                    Examination.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Examination findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Examination.class, id);
        } finally {
            em.close();
        }
    }
}