package com.university.dao;

import com.university.entity.Enrollment;
import com.university.util.DatabaseUtil;

import java.util.List;

public class EnrollmentDAO {

    public Enrollment save(Enrollment enrollment) {
        return DatabaseUtil.executeInTransaction(em -> {
            em.persist(enrollment);
            return enrollment;
        });
    }

    public boolean exists(Long studentId, Long courseId) {
        return DatabaseUtil.executeQuery(em -> {
            Long count = em.createQuery(
                            "select count(e) from Enrollment e "
                                    + "where e.student.id = :sid and e.course.id = :cid", Long.class)
                    .setParameter("sid", studentId)
                    .setParameter("cid", courseId)
                    .getSingleResult();
            return count > 0;
        });
    }

    public long countByCourse(Long courseId) {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select count(e) from Enrollment e where e.course.id = :cid", Long.class)
                .setParameter("cid", courseId)
                .getSingleResult());
    }

    public List<Enrollment> findByStudent(Long studentId) {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select e from Enrollment e where e.student.id = :sid order by e.id",
                        Enrollment.class)
                .setParameter("sid", studentId)
                .getResultList());
    }

    public List<Enrollment> findByCourse(Long courseId) {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select e from Enrollment e where e.course.id = :cid order by e.id",
                        Enrollment.class)
                .setParameter("cid", courseId)
                .getResultList());
    }
}
