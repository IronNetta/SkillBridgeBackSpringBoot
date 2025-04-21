package org.seba.repositories.custom.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.seba.entities.Review;
import org.seba.repositories.custom.ReviewRepositoryCustom;
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
        String base = """
            FROM Review r
            WHERE (:mentorId IS NULL OR r.mentor.id = :mentorId)
              AND (:studentId IS NULL OR r.author.id = :studentId)
              AND (:minRating IS NULL OR r.rating >= :minRating)
              AND (:maxRating IS NULL OR r.rating <= :maxRating)
        """;

        String jpql = "SELECT r " + base;
        String countJpql = "SELECT COUNT(r) " + base;

        TypedQuery<Review> query = em.createQuery(jpql, Review.class);
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);

        query.setParameter("mentorId", mentorId);
        query.setParameter("studentId", studentId);
        query.setParameter("minRating", minRating);
        query.setParameter("maxRating", maxRating);

        countQuery.setParameter("mentorId", mentorId);
        countQuery.setParameter("studentId", studentId);
        countQuery.setParameter("minRating", minRating);
        countQuery.setParameter("maxRating", maxRating);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Review> reviews = query.getResultList();
        long total = countQuery.getSingleResult();

        return new PageImpl<>(reviews, pageable, total);
    }
}
