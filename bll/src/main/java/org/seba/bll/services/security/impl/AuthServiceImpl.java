package org.seba.bll.services.security.impl;

import org.seba.dl.entities.Mentor;
import org.seba.dl.entities.Student;
import org.seba.dl.entities.User;
import org.seba.dl.enums.UserRole;
import org.seba.bll.exceptions.user.BadCredentialsException;
import org.seba.bll.exceptions.user.UserNotFoundException;
import org.seba.dal.repositories.UserRepository;
import org.seba.bll.services.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserNotFoundException(HttpStatus.NOT_ACCEPTABLE, "Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Si pas de rôle défini, on le force à STUDENT par défaut
        UserRole role = user.getRole();
        if (role == null || (role != UserRole.STUDENT && role != UserRole.MENTOR)) {
            role = UserRole.STUDENT;
        }

        // Instanciation correcte selon le rôle
        User newUser;
        if (role == UserRole.MENTOR) {
            Mentor mentor = new Mentor();
            mentor.setBio(""); // Optionnel, tu peux mettre un champ par défaut
            newUser = mentor;
        } else {
            newUser = new Student();
        }

        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setRole(role);

        userRepository.save(newUser);
    }


    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User with email " + email + " not found")
        );
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(HttpStatus.NOT_ACCEPTABLE, "Bad credentials");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND, email)
        );
    }
}