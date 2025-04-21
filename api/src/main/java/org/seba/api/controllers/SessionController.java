package org.seba.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.seba.api.models.CustomPage;
import org.seba.api.models.session.dtos.SessionDTO;
import org.seba.api.models.session.forms.SessionForm;
import org.seba.dl.entities.Session;
import org.seba.bll.services.session.SessionService;
import org.seba.bll.services.session.model.SessionInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SessionController {

    private final SessionService sessionService;

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping
    public ResponseEntity<SessionDTO> create(@RequestBody @Valid SessionForm form) {
        SessionInput input = new SessionInput(form.mentorId(), form.studentId(), form.date());
        Session session = sessionService.createFromInput(input);
        return ResponseEntity.ok(SessionDTO.fromEntity(session));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT', 'MENTOR')")
    @GetMapping
    public ResponseEntity<CustomPage<SessionDTO>> search(
            @RequestParam(required = false) Long mentorId,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime afterDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Session> sessions = sessionService.search(mentorId, studentId, status, afterDate, PageRequest.of(page - 1, size));
        return ResponseEntity.ok(new CustomPage<>(
                sessions.map(SessionDTO::fromEntity).getContent(),
                sessions.getTotalPages(),
                sessions.getNumber() + 1
        ));
    }

    @PreAuthorize("hasAuthority('MENTOR')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<SessionDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(SessionDTO.fromEntity(sessionService.updateStatus(id, status)));
    }
}
