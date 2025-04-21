package org.seba.api.models.session.forms;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SessionForm(
        @NotNull Long mentorId,
        @NotNull Long studentId,
        @NotNull @Future LocalDateTime date
) {
    // Le mapping se fait dans le service avec les entités récupérées via les repos
}