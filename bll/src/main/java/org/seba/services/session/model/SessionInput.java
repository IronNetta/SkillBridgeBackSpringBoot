package org.seba.services.session.model;

import java.time.LocalDateTime;

public record SessionInput(Long mentorId, Long studentId, LocalDateTime date) { }
