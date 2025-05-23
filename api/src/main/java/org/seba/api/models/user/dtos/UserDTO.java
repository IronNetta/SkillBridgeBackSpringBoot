package org.seba.api.models.user.dtos;

import org.seba.dl.entities.User;
import org.seba.dl.enums.UserRole;
import org.seba.dl.enums.UserStatus;

public record UserDTO(
        Long id,
        String username,
        String email,
        UserRole role,
        UserStatus status
) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getStatus());
    }
}
