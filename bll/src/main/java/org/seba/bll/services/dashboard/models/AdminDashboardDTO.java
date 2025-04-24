package org.seba.bll.services.dashboard.models;

public record AdminDashboardDTO(
        String role,
        long usersCount,
        long newMentors,
        long weeklySessions
) {}
