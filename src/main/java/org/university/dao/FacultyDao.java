package org.university.dao;

import org.university.entity.Faculty;
import org.university.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class FacultyDao {

    public void save(Faculty faculty) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(faculty);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Faculty> findAll() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT f FROM Faculty f ORDER BY f.id",
                    Faculty.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Faculty findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Faculty.class, id);
        } finally {
            em.close();
        }
    }
}