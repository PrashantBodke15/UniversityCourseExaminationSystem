package com.university.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.function.Function;

/**
 * Small helper so every DAO does not repeat begin/commit/rollback/close code.
 */
public final class DatabaseUtil {

    private DatabaseUtil() {
    }

    /** Runs work inside a transaction (insert / update / delete). */
    public static <T> T executeInTransaction(Function<EntityManager, T> work) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T result = work.apply(em);
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /** Runs a read-only query. */
    public static <T> T executeQuery(Function<EntityManager, T> work) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return work.apply(em);
        } finally {
            em.close();
        }
    }
}
