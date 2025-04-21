package org.seba.api.models.mentor.dtos;

import org.seba.dl.entities.Mentor;

public record MentorDTO(Long id, String username, String email, String bio, Double averageRating) {
    public static MentorDTO fromEntity(Mentor mentor, Double avgRating) {
        return new MentorDTO(mentor.getId(), mentor.getUsername(), mentor.getEmail(), mentor.getBio(), avgRating);
    }
}