package org.seba.api.models.skill.dtos;

import org.seba.dl.entities.Skill;

public record SkillDTO(Long id, String name) {
    public static SkillDTO fromEntity(Skill s) {
        return new SkillDTO(s.getId(), s.getName());
    }
}