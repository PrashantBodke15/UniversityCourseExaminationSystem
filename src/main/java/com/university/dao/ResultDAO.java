package com.university.dao;

import com.university.entity.Result;
import com.university.util.DatabaseUtil;

import java.util.List;

public class ResultDAO {

    public Result save(Result result) {
        return DatabaseUtil.executeInTransaction(em -> {
            em.persist(result);
            return result;
        });
    }

    public boolean exists(Long studentId, Long examinationId) {
        return DatabaseUtil.executeQuery(em -> {
            Long count = em.createQuery(
                            "select count(r) from Result r "
                                    + "where r.student.id = :sid and r.examination.id = :xid", Long.class)
                    .setParameter("sid", studentId)
                    .setParameter("xid", examinationId)
                    .getSingleResult();
            return count > 0;
        });
    }

    public List<Result> findByStudent(Long studentId) {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select r from Result r where r.student.id = :sid order by r.id", Result.class)
                .setParameter("sid", studentId)
                .getResultList());
    }

    public List<Result> findByExamination(Long examinationId) {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select r from Result r where r.examination.id = :xid order by r.id",
                        Result.class)
                .setParameter("xid", examinationId)
                .getResultList());
    }
}
