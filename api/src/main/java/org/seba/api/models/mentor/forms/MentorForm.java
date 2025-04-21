package org.seba.api.models.mentor.forms;

import org.seba.entities.Mentor;

import java.util.List;

public record MentorForm(String bio, List<Long> skillIds) {
    public void applyToEntity(Mentor mentor) {
        mentor.setBio(bio);
        // mapping des compétences à faire dans le service
    }
}