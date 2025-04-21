package org.seba.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.seba.api.models.CustomPage;
import org.seba.api.models.review.dtos.ReviewDTO;
import org.seba.api.models.review.forms.ReviewForm;
import org.seba.entities.Review;
import org.seba.services.review.ReviewService;
import org.seba.services.review.model.ReviewInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping
    public ResponseEntity<ReviewDTO> create(@RequestBody @Valid ReviewForm form) {
        ReviewInput input = new ReviewInput(form.mentorId(), form.studentId(), form.rating(), form.comment());
        Review review = reviewService.createFromInput(input);
        return ResponseEntity.ok(ReviewDTO.fromEntity(review));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'MENTOR')")
    @GetMapping
    public ResponseEntity<CustomPage<ReviewDTO>> search(
            @RequestParam(required = false) Long mentorId,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Review> reviews = reviewService.search(mentorId, studentId, minRating, maxRating, PageRequest.of(page - 1, size));
        return ResponseEntity.ok(new CustomPage<>(
                reviews.map(ReviewDTO::fromEntity).getContent(),
                reviews.getTotalPages(),
                reviews.getNumber() + 1
        ));
    }
}

