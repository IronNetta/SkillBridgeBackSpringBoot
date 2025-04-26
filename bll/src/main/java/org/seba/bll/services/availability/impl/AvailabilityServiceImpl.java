package org.seba.bll.services.availability.impl;

import lombok.RequiredArgsConstructor;
import org.seba.bll.services.availability.model.AvailabilityModel;
import org.seba.dal.repositories.SessionRepository;
import org.seba.dl.entities.Availability;
import org.seba.dl.entities.Mentor;
import org.seba.bll.exceptions.user.UserNotFoundException;
import org.seba.dal.repositories.AvailabilityRepository;
import org.seba.dal.repositories.MentorRepository;
import org.seba.bll.services.availability.AvailabilityService;
import org.seba.bll.services.availability.model.AvailabilityInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final SessionRepository sessionRepository;
    private final MentorRepository mentorRepository;

    @Override
    public Availability getById(Long id) {
        return availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + id));
    }

    @Override
    public List<AvailabilityModel> getByMentorId(Long mentorId) {
        List<Availability> availabilities = availabilityRepository.findByMentorId(mentorId);

        return availabilities.stream().map(a -> {
            boolean isBooked = sessionRepository.existsByMentorIdAndDateBetween(
                    mentorId,
                    a.getStartTime(),
                    a.getEndTime()
            );
            String status = isBooked ? "BOOKED" : "AVAILABLE";

            return AvailabilityModel.fromEntity(a, status);
        }).toList();
    }

    @Override
    public Page<Availability> getByMentorId(Long mentorId, Pageable pageable) {
        return availabilityRepository.findByMentorId(mentorId, pageable);
    }

    @Override
    public Availability create(Availability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    public void delete(Long id) {
        availabilityRepository.deleteById(id);
    }

    @Override
    public Availability createFromInput(AvailabilityInput input) {
        Mentor mentor = mentorRepository.findById(input.mentorId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Mentor not found"));

        Availability availability = new Availability();
        availability.setMentor(mentor);
        availability.setStartTime(input.start());
        availability.setEndTime(input.end());

        return availabilityRepository.save(availability);
    }
}
