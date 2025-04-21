package org.seba.api.controllers;

import lombok.RequiredArgsConstructor;
import org.seba.api.models.student.dtos.StudentDTO;
import org.seba.entities.Student;
import org.seba.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StudentController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('STUDENT', 'MENTOR', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(StudentDTO.fromEntity((Student) userService.getUserById(id)));
    }
}
