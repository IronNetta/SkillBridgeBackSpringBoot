package org.seba.api.models.security.dtos;

import org.seba.dl.entities.User;
import org.seba.dl.enums.UserRole;

public record UserSessionDTO(
        Long id,
        UserRole role,
        String username,
        String email
) {
    public static UserSessionDTO fromUser(User user) {
        return new UserSessionDTO(
                user.getId(),
                user.getRole(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
