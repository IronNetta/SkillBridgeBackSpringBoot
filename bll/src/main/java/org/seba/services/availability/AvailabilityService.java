package org.seba.services.availability;

import org.seba.entities.Availability;
import org.seba.services.availability.model.AvailabilityInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AvailabilityService {

    Availability getById(Long id);

    List<Availability> getByMentorId(Long mentorId);

    Page<Availability> getByMentorId(Long mentorId, Pageable pageable);

    Availability create(Availability availability);

    void delete(Long id);

    Availability createFromInput(AvailabilityInput input);
}
