package org.seba.bll.services.availability.model;

import org.seba.dl.entities.Availability;

import java.time.LocalDateTime;

public record AvailabilityModel(
        Long id,
        Long mentorId,
        LocalDateTime start,
        LocalDateTime end,
        String status // "AVAILABLE" ou "BOOKED"
) {

    public static AvailabilityModel fromEntity(Availability a, String status) {
        return new AvailabilityModel(
                a.getId(),
                a.getMentor().getId(),
                a.getStartTime(),
                a.getEndTime(),
                status
        );
    }
}

