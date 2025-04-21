package org.seba.api.models.skill.forms;

import jakarta.validation.constraints.NotBlank;

public record SkillForm(@NotBlank String name) { }