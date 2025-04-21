package org.seba.api.models.student.forms;

import org.seba.entities.Student;

public record StudentForm(String username, String email, String password) {
    public Student toEntity() {
        Student s = new Student();
        s.setUsername(username);
        s.setEmail(email);
        s.setPassword(password);
        return s;
    }
}