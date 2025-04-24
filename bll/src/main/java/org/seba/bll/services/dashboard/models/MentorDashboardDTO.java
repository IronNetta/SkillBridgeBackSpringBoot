package org.seba.bll.services.dashboard.models;


public record MentorDashboardDTO(
        String role,
        long upcomingSessions,
        double averageRating,
        long notifications
) {}