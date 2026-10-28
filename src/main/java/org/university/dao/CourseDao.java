package org.university.dao;

import org.university.entity.Course;
import org.university.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CourseDao {

    public void save(Course course) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(course);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Course> findAll() {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.createQuery(
                    "SELECT c FROM Course c ORDER BY c.id",
                    Course.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Course findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            return em.find(Course.class, id);
        } finally {
            em.close();
        }
    }
}