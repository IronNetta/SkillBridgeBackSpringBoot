package org.seba.api.models.user.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.seba.dl.entities.User;
import org.seba.dl.enums.UserRole;

public record UserForm(
        @NotBlank @Size(max = 50) String username,
        @NotBlank @Email String email,
        @NotBlank String password,
        UserRole role
) {
    public User toUser() {
        return new User(username, email, password);
    }
}
