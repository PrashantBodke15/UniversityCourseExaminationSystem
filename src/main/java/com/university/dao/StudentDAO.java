package com.university.dao;

import com.university.entity.Student;
import com.university.util.DatabaseUtil;

import java.util.List;
import java.util.Optional;

public class StudentDAO {

    public Student save(Student student) {
        return DatabaseUtil.executeInTransaction(em -> {
            em.persist(student);
            return student;
        });
    }

    public Student update(Student student) {
        return DatabaseUtil.executeInTransaction(em -> em.merge(student));
    }

    public void delete(Long id) {
        DatabaseUtil.executeInTransaction(em -> {
            Student s = em.find(Student.class, id);
            if (s != null) {
                em.remove(s);
            }
            return null;
        });
    }

    public Optional<Student> findById(Long id) {
        return DatabaseUtil.executeQuery(em -> Optional.ofNullable(em.find(Student.class, id)));
    }

    public Optional<Student> findByEmail(String email) {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select s from Student s where s.email = :email", Student.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst());
    }

    public List<Student> findAll() {
        return DatabaseUtil.executeQuery(em -> em
                .createQuery("select s from Student s order by s.id", Student.class)
                .getResultList());
    }
}
