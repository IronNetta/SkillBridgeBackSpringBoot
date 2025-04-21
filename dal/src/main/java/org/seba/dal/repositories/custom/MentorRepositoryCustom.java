package org.seba.dal.repositories.custom;

import org.seba.dl.entities.Mentor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface MentorRepositoryCustom {
    Page<Mentor> searchMentors(String skill, Double minRating, LocalDateTime availableAfter, Pageable pageable);
}