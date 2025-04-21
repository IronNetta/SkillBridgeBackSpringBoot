package org.seba.api.controllers;

import lombok.RequiredArgsConstructor;
import org.seba.api.models.CustomPage;
import org.seba.api.models.mentor.dtos.MentorDTO;
import org.seba.entities.Mentor;
import org.seba.repositories.MentorRepository;
import org.seba.services.mentor.MentorService;
import org.seba.services.mentor.model.MentorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mentors")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class MentorController {

    private final MentorService mentorService;

    @GetMapping
    public ResponseEntity<CustomPage<MentorDTO>> searchMentors(
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime availableAfter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<MentorModel> mentors = mentorService.search(skill, minRating, availableAfter, pageable);
        List<MentorDTO> dtos = mentors.map(m -> new MentorDTO(m.id(), m.username(), m.email(), m.bio(), m.averageRating())).getContent();
        return ResponseEntity.ok(new CustomPage<>(dtos, mentors.getTotalPages(), mentors.getNumber() + 1));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getById(@PathVariable Long id) {
        MentorModel model = mentorService.getWithAverageById(id);
        return ResponseEntity.ok(new MentorDTO(model.id(), model.username(), model.email(), model.bio(), model.averageRating()));
    }
}
