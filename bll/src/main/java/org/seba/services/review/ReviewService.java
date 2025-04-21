package org.seba.services.review;

import org.seba.entities.Review;
import org.seba.services.review.model.ReviewInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Review getById(Long id);

    Page<Review> getByMentor(Long mentorId, Pageable pageable);

    Page<Review> getByStudent(Long studentId, Pageable pageable);

    Page<Review> search(Long mentorId, Long studentId, Integer minRating, Integer maxRating, Pageable pageable);

    Review create(Review review);

    void delete(Long id);

    Review createFromInput(ReviewInput input);
}
