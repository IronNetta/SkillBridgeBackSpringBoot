package org.seba.api.models.review.forms;

import jakarta.validation.constraints.*;

public record ReviewForm(
        @NotNull Long mentorId,
        @NotNull Long studentId,
        @Min(1) @Max(5) int rating,
        @Size(max = 500) String comment
) {
    // Le mapping se fait dans le service avec les entités Mentor & Student
}