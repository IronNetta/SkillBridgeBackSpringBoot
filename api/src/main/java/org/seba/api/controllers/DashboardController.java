package org.seba.api.controllers;

import lombok.RequiredArgsConstructor;
import org.seba.dl.entities.Review;
import org.seba.dl.entities.User;
import org.seba.bll.services.review.ReviewService;
import org.seba.bll.services.session.SessionService;
import org.seba.bll.services.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final SessionService sessionService;
    private final ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboard(@AuthenticationPrincipal UserDetails currentUser) {
        String email = currentUser.getUsername();
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
                double avgRating = reviewService.getByMentor(user.getId(), Pageable.unpaged())
                        .getContent()
                        .stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);
                data.put("upcomingSessions", upcomingSessions);
                data.put("averageRating", avgRating);
                data.put("notifications", 1); // placeholder
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
