package org.seba.api.models.security.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.seba.dl.entities.User;
import org.seba.dl.enums.UserRole;

public record RegisterForm(
        @NotBlank @Size(max = 50)
        String username,
        @NotBlank @Size(max = 150)
        String email,
        @NotBlank
        String password,
        UserRole role
) {

    public User toUser() {
        User user = new User(
                username,
                email,
                password
        );
        if (role != null) {
            user.setRole(role);
        }
        return user;
    }
}