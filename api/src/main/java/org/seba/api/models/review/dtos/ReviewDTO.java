package org.seba.api.models.review.dtos;

import org.seba.dl.entities.Review;

public record ReviewDTO(Long id, int rating, String comment, Long mentorId, Long authorId) {
    public static ReviewDTO fromEntity(Review r) {
        return new ReviewDTO(r.getId(), r.getRating(), r.getComment(), r.getMentor().getId(), r.getAuthor().getId());
    }
}