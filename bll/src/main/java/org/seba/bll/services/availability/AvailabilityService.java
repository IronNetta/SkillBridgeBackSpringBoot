package org.seba.bll.services.availability;

import org.seba.bll.services.availability.model.AvailabilityModel;
import org.seba.dl.entities.Availability;
import org.seba.bll.services.availability.model.AvailabilityInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AvailabilityService {

    Availability getById(Long id);

    List<AvailabilityModel> getByMentorId(Long mentorId);

    Page<Availability> getByMentorId(Long mentorId, Pageable pageable);

    Availability create(Availability availability);

    void delete(Long id);

    Availability createFromInput(AvailabilityInput input);
}
