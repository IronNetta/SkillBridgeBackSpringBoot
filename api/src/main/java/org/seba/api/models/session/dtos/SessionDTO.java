package org.seba.api.models.session.dtos;

import org.seba.entities.Session;

import java.time.LocalDateTime;

public record SessionDTO(
        Long id,
        Long mentorId,
        Long studentId,
        LocalDateTime date,
        String status
) {
    public static SessionDTO fromEntity(Session session) {
        return new SessionDTO(
                session.getId(),
                session.getMentor().getId(),
                session.getStudent().getId(),
                session.getDate(),
                session.getStatus()
        );
    }
}