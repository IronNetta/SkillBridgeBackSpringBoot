package org.seba.dal.repositories;

import org.seba.dl.entities.Review;
import org.seba.dal.repositories.custom.ReviewRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    List<Review> findByMentorId(Long mentorId);
    List<Review> findByAuthorId(Long studentId);
    Page<Review> findByMentorId(Long mentorId, Pageable pageable);
    Page<Review> findByAuthorId(Long studentId, Pageable pageable);
}
