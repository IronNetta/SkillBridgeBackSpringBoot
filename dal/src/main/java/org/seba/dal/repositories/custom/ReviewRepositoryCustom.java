package org.seba.dal.repositories.custom;

import org.seba.dl.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<Review> searchReviews(Long mentorId, Long studentId, Integer minRating, Integer maxRating, Pageable pageable);
}