package org.seba.bll.services.mentor;

import org.seba.dl.entities.Mentor;
import org.seba.bll.services.mentor.model.MentorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface MentorService {

    Mentor getById(Long id);

    Page<Mentor> getAll(Pageable pageable);

    Page<MentorModel> search(String skill, Double minRating, LocalDateTime availableAfter, Pageable pageable);

    MentorModel getWithAverageById(Long id);
}
