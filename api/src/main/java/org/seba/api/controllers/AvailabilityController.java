package org.seba.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.seba.api.models.availability.dtos.AvailabilityDTO;
import org.seba.api.models.availability.forms.AvailabilityForm;
import org.seba.dl.entities.Availability;
import org.seba.bll.services.availability.AvailabilityService;
import org.seba.bll.services.availability.model.AvailabilityInput;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availabilities")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PreAuthorize("hasAuthority('MENTOR')")
    @PostMapping
    public ResponseEntity<AvailabilityDTO> create(@RequestBody @Valid AvailabilityForm form) {
        AvailabilityInput input = new AvailabilityInput(form.mentorId(), form.start(), form.end());
        Availability availability = availabilityService.createFromInput(input);
        return ResponseEntity.ok(AvailabilityDTO.fromEntity(availability));
    }

    @PreAuthorize("hasAnyAuthority('MENTOR', 'STUDENT')")
    @GetMapping("/mentor/{id}")
    public ResponseEntity<List<AvailabilityDTO>> getByMentor(@PathVariable Long id) {
        return ResponseEntity.ok(
                availabilityService.getByMentorId(id).stream().map(AvailabilityDTO::fromEntity).toList()
        );
    }
}

