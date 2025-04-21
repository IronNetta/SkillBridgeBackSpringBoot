package org.seba.repositories.custom;

import org.seba.entities.Mentor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface MentorRepositoryCustom {
    Page<Mentor> searchMentors(String skill, Double minRating, LocalDateTime availableAfter, Pageable pageable);
}