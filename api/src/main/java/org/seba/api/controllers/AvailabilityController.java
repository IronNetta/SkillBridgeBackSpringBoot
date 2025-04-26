package org.seba.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.seba.api.models.availability.dtos.AvailabilityDTO;
import org.seba.api.models.availability.forms.AvailabilityForm;
import org.seba.bll.services.availability.model.AvailabilityModel;
import org.seba.dl.entities.Availability;
import org.seba.bll.services.availability.AvailabilityService;
import org.seba.bll.services.availability.model.AvailabilityInput;
import org.seba.dl.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/availabilities")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PreAuthorize("hasAuthority('MENTOR')")
    @GetMapping("/me")
    public ResponseEntity<List<AvailabilityDTO>> getMine(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
                availabilityService.getByMentorId(user.getId())
                        .stream()
                        .map(AvailabilityDTO::fromModel)
                        .toList()
        );
    }

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
        List<AvailabilityModel> models = availabilityService.getByMentorId(id);
        List<AvailabilityDTO> dtos = models.stream()
                .map(AvailabilityDTO::fromModel)
                .toList();

        return ResponseEntity.ok(dtos);
    }



    @PreAuthorize("hasAuthority('MENTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        availabilityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityDTO> update(@PathVariable Long id, @RequestBody @Valid AvailabilityForm form) {
        Availability availability = availabilityService.getById(id);
        availability.setStartTime(form.start());
        availability.setEndTime(form.end());
        availabilityService.create(availability); // re-save
        return ResponseEntity.ok(AvailabilityDTO.fromEntity(availability));
    }
}

