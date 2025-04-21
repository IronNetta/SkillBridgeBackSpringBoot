package org.seba.api.models.availability.dtos;

import org.seba.entities.Availability;

import java.time.LocalDateTime;

public record AvailabilityDTO(Long id, Long mentorId, LocalDateTime start, LocalDateTime end) {
    public static AvailabilityDTO fromEntity(Availability a) {
        return new AvailabilityDTO(a.getId(), a.getMentor().getId(), a.getStartTime(), a.getEndTime());
    }
}