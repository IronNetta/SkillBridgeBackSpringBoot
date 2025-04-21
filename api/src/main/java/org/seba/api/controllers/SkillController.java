package org.seba.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.seba.api.models.skill.dtos.SkillDTO;
import org.seba.api.models.skill.forms.SkillForm;
import org.seba.dl.entities.Skill;
import org.seba.bll.services.skill.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAll() {
        return ResponseEntity.ok(
                skillService.getAll().stream().map(SkillDTO::fromEntity).toList()
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<SkillDTO> create(@RequestBody @Valid SkillForm form) {
        return ResponseEntity.ok(SkillDTO.fromEntity(skillService.create(new Skill(form.name()))));
    }
}
