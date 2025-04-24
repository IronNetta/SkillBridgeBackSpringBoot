package org.seba.dal.repositories.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.seba.dl.entities.Mentor;
import org.seba.dal.repositories.custom.MentorRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MentorRepositoryImpl implements MentorRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Mentor> searchMentors(String skill, Double minRating, LocalDateTime availableAfter, Pageable pageable) {
        StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT m FROM Mentor m " +
                        "JOIN m.skills s " +
                        "LEFT JOIN m.reviews r " +
                        "JOIN m.availabilities a WHERE 1=1 "
        );

        if (skill != null) {
            jpql.append(" AND s.name = :skill");
        }

        if (minRating != null) {
            jpql.append(" AND (SELECT AVG(r2.rating) FROM Review r2 WHERE r2.mentor = m) >= :minRating");
        }

        if (availableAfter != null) {
            jpql.append(" AND a.startTime > :availableAfter");
        }

        TypedQuery<Mentor> query = entityManager.createQuery(jpql.toString(), Mentor.class);

        if (skill != null) {
            query.setParameter("skill", skill);
        }

        if (minRating != null) {
            query.setParameter("minRating", minRating);
        }

        if (availableAfter != null) {
            query.setParameter("availableAfter", availableAfter);
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Mentor> mentors = query.getResultList();

        StringBuilder countJpql = new StringBuilder(
                "SELECT COUNT(DISTINCT m) FROM Mentor m " +
                        "JOIN m.skills s " +
                        "LEFT JOIN m.reviews r " +
                        "JOIN m.availabilities a WHERE 1=1 "
        );

        if (skill != null) {
            countJpql.append(" AND s.name = :skill");
        }

        if (minRating != null) {
            countJpql.append(" AND (SELECT AVG(r2.rating) FROM Review r2 WHERE r2.mentor = m) >= :minRating");
        }

        if (availableAfter != null) {
            countJpql.append(" AND a.startTime > :availableAfter");
        }

        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);

        if (skill != null) {
            countQuery.setParameter("skill", skill);
        }

        if (minRating != null) {
            countQuery.setParameter("minRating", minRating);
        }

        if (availableAfter != null) {
            countQuery.setParameter("availableAfter", availableAfter);
        }

        long total = countQuery.getSingleResult();

        return new PageImpl<>(mentors, pageable, total);
    }

}