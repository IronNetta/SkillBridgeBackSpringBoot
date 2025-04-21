package org.seba.api.models.availability.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;

import java.time.LocalDateTime;

public record AvailabilityForm(
        @NotNull Long mentorId,
        @NotNull @Future LocalDateTime start,
        @NotNull @Future LocalDateTime end
) {
    // À mapper dans le service
}
