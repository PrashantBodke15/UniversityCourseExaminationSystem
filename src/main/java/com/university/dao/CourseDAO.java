package com.university.dao;

import com.university.entity.Course;
import com.university.util.DatabaseUtil;

import java.util.List;
import java.util.Optional;

public class CourseDAO {

    public Course save(Course course) {
        return DatabaseUtil.executeInTransaction(em -> {
            em.persist(course);
            return course;
        });
    }

    public Optional<Course> findById(Long id) {
        return DatabaseUtil.executeQuery(em -> Optional.ofNullable(em.find(Course.class, id)));
    }

    public Optional<Course> findByCode(String code) {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select c from Course c where c.code = :code", Course.class)
                .setParameter("code", code)
                .getResultStream()
                .findFirst());
    }

    public List<Course> findAll() {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select c from Course c order by c.id", Course.class)
                .getResultList());
    }
}
