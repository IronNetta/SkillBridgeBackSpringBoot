package org.seba.repositories.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.seba.entities.Session;
import org.seba.repositories.custom.SessionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class SessionRepositoryImpl implements SessionRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Session> searchSessions(Long mentorId, Long studentId, String status, LocalDateTime afterDate, Pageable pageable) {
        String base = """
            FROM Session s
            WHERE (:mentorId IS NULL OR s.mentor.id = :mentorId)
              AND (:studentId IS NULL OR s.student.id = :studentId)
              AND (:status IS NULL OR s.status = :status)
              AND (:afterDate IS NULL OR s.date >= :afterDate)
        """;

        String jpql = "SELECT s " + base;
        String countJpql = "SELECT COUNT(s) " + base;

        TypedQuery<Session> query = em.createQuery(jpql, Session.class);
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);

        query.setParameter("mentorId", mentorId);
        query.setParameter("studentId", studentId);
        query.setParameter("status", status);
        query.setParameter("afterDate", afterDate);

        countQuery.setParameter("mentorId", mentorId);
        countQuery.setParameter("studentId", studentId);
        countQuery.setParameter("status", status);
        countQuery.setParameter("afterDate", afterDate);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }
}

