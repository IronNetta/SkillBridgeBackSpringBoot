package org.seba.services.skill;

import org.seba.entities.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    List<Skill> getAll();

    Optional<Skill> getByName(String name);

    Skill create(Skill skill);

    void delete(Long id);
}