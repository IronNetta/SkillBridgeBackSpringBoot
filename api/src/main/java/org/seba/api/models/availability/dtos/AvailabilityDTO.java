package org.seba.api.models.availability.dtos;

import org.seba.dl.entities.Availability;
import org.seba.bll.services.availability.model.AvailabilityModel;

import java.time.LocalDateTime;

public record AvailabilityDTO(
        Long id,
        Long mentorId,
        LocalDateTime start,
        LocalDateTime end,
        String status
) {
    public static AvailabilityDTO fromEntity(Availability a) {
        return new AvailabilityDTO(
                a.getId(),
                a.getMentor().getId(),
                a.getStartTime(),
                a.getEndTime(),
                "AVAILABLE" // par défaut quand on n'a pas de status
        );
    }

    public static AvailabilityDTO fromModel(AvailabilityModel model) {
        return new AvailabilityDTO(
                model.id(),
                model.mentorId(),
                model.start(),
                model.end(),
                model.status()
        );
    }
}
