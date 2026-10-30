package com.university.dao;

import com.university.entity.Examination;
import com.university.util.DatabaseUtil;

import java.util.List;
import java.util.Optional;

public class ExaminationDAO {

    public Examination save(Examination examination) {
        return DatabaseUtil.executeInTransaction(em -> {
            em.persist(examination);
            return examination;
        });
    }

    public Optional<Examination> findById(Long id) {
        return DatabaseUtil.executeQuery(em -> Optional.ofNullable(em.find(Examination.class, id)));
    }

    public List<Examination> findByCourse(Long courseId) {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select x from Examination x where x.course.id = :cid order by x.examDate",
                        Examination.class)
                .setParameter("cid", courseId)
                .getResultList());
    }

    public List<Examination> findAll() {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select x from Examination x order by x.examDate", Examination.class)
                .getResultList());
    }
}
