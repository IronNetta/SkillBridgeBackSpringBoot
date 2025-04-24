package org.seba.bll.services.mentor.impl;

import lombok.RequiredArgsConstructor;
import org.seba.dl.entities.Mentor;
import org.seba.bll.exceptions.user.UserNotFoundException;
import org.seba.dal.repositories.MentorRepository;
import org.seba.bll.services.mentor.MentorService;
import org.seba.bll.services.mentor.model.MentorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;

    @Override
    public Mentor getById(Long id) {
        return mentorRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Mentor not found with id: " + id));
    }

    @Override
    public Page<Mentor> getAll(Pageable pageable) {
        return mentorRepository.findAllWithCreatedAt(pageable);
    }

    @Override
    public Page<MentorModel> search(String skill, Double minRating, LocalDateTime availableAfter, Pageable pageable) {
        Page<Mentor> mentors = mentorRepository.searchMentors(skill, minRating, availableAfter, pageable);
        return mentors.map(m -> {
            Double avg = mentorRepository.getAverageRatingByMentorId(m.getId());
            return new MentorModel(m.getId(), m.getUsername(), m.getEmail(), m.getBio(), avg);
        });
    }


    @Override
    public MentorModel getWithAverageById(Long id) {
        Mentor m = getById(id);
        Double avg = mentorRepository.getAverageRatingByMentorId(id);
        return new MentorModel(m.getId(), m.getUsername(), m.getEmail(), m.getBio(), avg);
    }
}