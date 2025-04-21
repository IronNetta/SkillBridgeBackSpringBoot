package org.seba.api.controllers;

import lombok.RequiredArgsConstructor;
import org.seba.entities.Review;
import org.seba.entities.User;
import org.seba.services.review.ReviewService;
import org.seba.services.session.SessionService;
import org.seba.services.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class DashboardController {

    private final UserService userService;
    private final SessionService sessionService;

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
        }

        String email = authentication.getName(); // c’est ton login/email
        User user = userService.getUserByEmail(email);

        Map<String, Object> data = new HashMap<>();
        data.put("role", user.getRole());

        switch (user.getRole()) {
            case STUDENT -> {
                int sessionCount = sessionService.getByStudent(user.getId(), Pageable.unpaged()).getNumberOfElements();
                int feedbacks = reviewService.getByStudent(user.getId(), Pageable.unpaged()).getNumberOfElements();
                data.put("sessionsCount", sessionCount);
                data.put("feedbacks", feedbacks);
                data.put("recommendedMentors", 3);
            }
            case MENTOR -> {
                int upcomingSessions = sessionService.getByMentor(user.getId(), Pageable.unpaged()).getNumberOfElements();
                double avgRating = Optional.of(reviewService.getByMentor(user.getId(), Pageable.unpaged())
                        .getContent()
                        .stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0)).orElse(0D);
                data.put("upcomingSessions", upcomingSessions);
                data.put("averageRating", avgRating);
                data.put("notifications", 1);
            }
            case ADMIN -> {
                data.put("usersCount", userService.getUsers(List.of(), Pageable.unpaged()).getNumberOfElements());
                data.put("newMentors", 8);
                data.put("weeklySessions", 42);
            }
        }

        return ResponseEntity.ok(data);
    }

}
