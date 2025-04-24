package org.seba.bll.services.dashboard.impl;

import lombok.RequiredArgsConstructor;
import org.seba.bll.services.dashboard.DashboardService;
import org.seba.bll.services.dashboard.models.AdminDashboardDTO;
import org.seba.bll.services.dashboard.models.MentorDashboardDTO;
import org.seba.bll.services.dashboard.models.StudentDashboardDTO;
import org.seba.bll.services.mentor.MentorService;
import org.seba.bll.services.mentor.model.MentorModel;
import org.seba.bll.services.review.ReviewService;
import org.seba.bll.services.session.SessionService;
import org.seba.bll.services.user.UserService;
import org.seba.dl.entities.Mentor;
import org.seba.dl.entities.Review;
import org.seba.dl.entities.Session;
import org.seba.dl.entities.User;
import org.seba.dl.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserService userService;
    private final SessionService sessionService;
    private final ReviewService reviewService;
    private final MentorService mentorService;

    @Override
    public Object getDashboardForUser(String email) {
        User user = userService.getUserByEmail(email);
        UserRole role = user.getRole();

        switch (role) {
            case STUDENT -> {
                Page<Session> sessions = sessionService.getByStudent(user.getId(), Pageable.unpaged());
                Page<Review> reviews = reviewService.getByStudent(user.getId(), Pageable.unpaged());
                Page<MentorModel> recommended = mentorService.search(null, 4.0, null, Pageable.ofSize(3));

                return new StudentDashboardDTO(
                        "STUDENT",
                        sessions.getNumberOfElements(),
                        reviews.getNumberOfElements(),
                        recommended.getContent()
                );
            }

            case MENTOR -> {
                Page<Session> sessions = sessionService.getByMentor(user.getId(), Pageable.unpaged());
                Page<Review> reviews = reviewService.getByMentor(user.getId(), Pageable.unpaged());
                double avgRating = reviews.getContent().stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);
                long pendingCount = sessionService.search(user.getId(), null, "PENDING", null, Pageable.unpaged()).getTotalElements();

                return new MentorDashboardDTO(
                        "MENTOR",
                        sessions.getNumberOfElements(),
                        avgRating,
                        pendingCount
                );
            }

            case ADMIN -> {
                try {
                    System.out.println("📥 Chargement des utilisateurs...");
                    Page<User> users = userService.getUsers(List.of(), Pageable.unpaged());

                    System.out.println("📥 Chargement des mentors...");
                    Page<Mentor> mentors = mentorService.getAll(Pageable.unpaged());

                    System.out.println("📊 Calcul des nouveaux mentors...");
                    long newMentors = mentors.getContent()
                            .stream()
                            .filter(m -> {
                                try {
                                    return m.getCreatedAt() != null &&
                                            m.getCreatedAt().isAfter(LocalDateTime.now().minusDays(7));
                                } catch (UnsupportedOperationException e) {
                                    System.err.println("⚠️ CreatedAt non disponible pour mentor : " + m.getId());
                                    return false;
                                }
                            })
                            .count();

                    System.out.println("📈 Calcul des sessions récentes...");
                    long weeklySessions = sessionService.search(
                            null, null, null,
                            LocalDateTime.now().minusDays(7),
                            Pageable.unpaged()
                    ).getTotalElements();

                    System.out.println("✅ Dashboard Admin généré !");
                    return new AdminDashboardDTO(
                            "ADMIN",
                            users.getNumberOfElements(),
                            newMentors,
                            weeklySessions
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Erreur interne dans le dashboard ADMIN : " + e.getMessage());
                }
            }


            default -> throw new IllegalStateException("Unexpected role: " + role);
        }
    }
}

