package org.seba.api.models.student.dtos;

import org.seba.entities.Student;

public record StudentDTO(Long id, String username, String email) {
    public static StudentDTO fromEntity(Student student) {
        return new StudentDTO(student.getId(), student.getUsername(), student.getEmail());
    }
}