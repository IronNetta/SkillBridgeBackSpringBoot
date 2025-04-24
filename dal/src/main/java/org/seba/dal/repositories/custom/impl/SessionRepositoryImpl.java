package org.seba.dal.repositories.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import org.seba.dl.entities.Session;
import org.seba.dal.repositories.custom.SessionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;

import java.time.LocalDateTime;

@Repository
public class SessionRepositoryImpl implements SessionRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Session> searchSessions(Long mentorId, Long studentId, String status, LocalDateTime afterDate, Pageable pageable) {
        StringBuilder jpqlBuilder = new StringBuilder("SELECT s FROM Session s");
        StringBuilder countJpqlBuilder = new StringBuilder("SELECT COUNT(s) FROM Session s");
        StringBuilder conditionsBuilder = new StringBuilder(" WHERE 1=1");

        if (mentorId != null) conditionsBuilder.append(" AND s.mentor.id = :mentorId");
        if (studentId != null) conditionsBuilder.append(" AND s.student.id = :studentId");
        if (status != null) conditionsBuilder.append(" AND s.status = :status");
        if (afterDate != null) conditionsBuilder.append(" AND s.date >= :afterDate");

        String jpql = jpqlBuilder.append(conditionsBuilder).toString();
        String countJpql = countJpqlBuilder.append(conditionsBuilder).toString();

        TypedQuery<Session> query = em.createQuery(jpql, Session.class);
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);

        if (mentorId != null) {
            query.setParameter("mentorId", mentorId);
            countQuery.setParameter("mentorId", mentorId);
        }
        if (studentId != null) {
            query.setParameter("studentId", studentId);
            countQuery.setParameter("studentId", studentId);
        }
        if (status != null) {
            query.setParameter("status", status);
            countQuery.setParameter("status", status);
        }
        if (afterDate != null) {
            query.setParameter("afterDate", afterDate);
            countQuery.setParameter("afterDate", afterDate);
        }

        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }
}