package com.university.dao;

import com.university.entity.Faculty;
import com.university.util.DatabaseUtil;

import java.util.List;
import java.util.Optional;

public class FacultyDAO {

    public Faculty save(Faculty faculty) {
        return DatabaseUtil.executeInTransaction(em -> {
            em.persist(faculty);
            return faculty;
        });
    }

    public Optional<Faculty> findById(Long id) {
        return DatabaseUtil.executeQuery(em -> Optional.ofNullable(em.find(Faculty.class, id)));
    }

    public List<Faculty> findAll() {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select f from Faculty f order by f.id", Faculty.class)
                .getResultList());
    }
}
