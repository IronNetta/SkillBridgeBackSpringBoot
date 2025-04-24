package org.seba.bll.services.dashboard.models;

import org.seba.bll.services.mentor.model.MentorModel;
import java.util.List;

public record StudentDashboardDTO(
        String role,
        long sessionsCount,
        long feedbacks,
        List<MentorModel> recommendedMentors
) {}
