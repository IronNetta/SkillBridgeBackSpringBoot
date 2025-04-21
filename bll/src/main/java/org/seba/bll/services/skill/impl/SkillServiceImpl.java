package org.seba.bll.services.skill.impl;

import lombok.RequiredArgsConstructor;
import org.seba.dl.entities.Skill;
import org.seba.dal.repositories.SkillRepository;
import org.seba.bll.services.skill.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    @Override
    public Optional<Skill> getByName(String name) {
        return skillRepository.findByName(name);
    }

    @Override
    public Skill create(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public void delete(Long id) {
        skillRepository.deleteById(id);
    }
}
