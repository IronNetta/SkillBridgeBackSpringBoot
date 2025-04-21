package org.seba.services.review.impl;

import lombok.RequiredArgsConstructor;
import org.seba.entities.Mentor;
import org.seba.entities.Review;
import org.seba.entities.Student;
import org.seba.exceptions.GlobalException;
import org.seba.exceptions.user.UserNotFoundException;
import org.seba.repositories.MentorRepository;
import org.seba.repositories.ReviewRepository;
import org.seba.repositories.UserRepository;
import org.seba.services.review.ReviewService;
import org.seba.services.review.model.ReviewInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;

    @Override
    public Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "Review not found with id: " + id) {});
    }

    @Override
    public Page<Review> getByMentor(Long mentorId, Pageable pageable) {
        return reviewRepository.findByMentorId(mentorId, pageable);
    }

    @Override
    public Page<Review> getByStudent(Long studentId, Pageable pageable) {
        return reviewRepository.findByAuthorId(studentId, pageable);
    }

    @Override
    public Page<Review> search(Long mentorId, Long studentId, Integer minRating, Integer maxRating, Pageable pageable) {
        return reviewRepository.searchReviews(mentorId, studentId, minRating, maxRating, pageable);
    }

    @Override
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Review createFromInput(ReviewInput input) {
        Mentor mentor = mentorRepository.findById(input.mentorId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Mentor not found"));

        Student student = (Student) userRepository.findById(input.studentId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Student not found"));

        Review review = new Review();
        review.setRating(input.rating());
        review.setComment(input.comment());
        review.setMentor(mentor);
        review.setAuthor(student);

        return reviewRepository.save(review);
    }

}
