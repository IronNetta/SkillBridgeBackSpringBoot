package org.seba.dal.repositories.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.seba.dl.entities.Review;
import org.seba.dal.repositories.custom.ReviewRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Review> searchReviews(Long mentorId, Long studentId, Integer minRating, Integer maxRating, Pageable pageable) {
        StringBuilder jpqlBuilder = new StringBuilder("SELECT r FROM Review r");
        StringBuilder countJpqlBuilder = new StringBuilder("SELECT COUNT(r) FROM Review r");
        StringBuilder conditionsBuilder = new StringBuilder(" WHERE 1=1");

        if (mentorId != null) conditionsBuilder.append(" AND r.mentor.id = :mentorId");
        if (studentId != null) conditionsBuilder.append(" AND r.author.id = :studentId");
        if (minRating != null) conditionsBuilder.append(" AND r.rating >= :minRating");
        if (maxRating != null) conditionsBuilder.append(" AND r.rating <= :maxRating");

        String jpql = jpqlBuilder.append(conditionsBuilder).toString();
        String countJpql = countJpqlBuilder.append(conditionsBuilder).toString();

        TypedQuery<Review> query = em.createQuery(jpql, Review.class);
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);

        if (mentorId != null) {
            query.setParameter("mentorId", mentorId);
            countQuery.setParameter("mentorId", mentorId);
        }
        if (studentId != null) {
            query.setParameter("studentId", studentId);
            countQuery.setParameter("studentId", studentId);
        }
        if (minRating != null) {
            query.setParameter("minRating", minRating);
            countQuery.setParameter("minRating", minRating);
        }
        if (maxRating != null) {
            query.setParameter("maxRating", maxRating);
            countQuery.setParameter("maxRating", maxRating);
        }

        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return new PageImpl<>(query.getResultList(), pageable, countQuery.getSingleResult());
    }
}