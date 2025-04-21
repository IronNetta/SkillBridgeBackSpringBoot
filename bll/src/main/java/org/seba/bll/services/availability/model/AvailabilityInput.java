package org.seba.bll.services.availability.model;

import java.time.LocalDateTime;

public record AvailabilityInput(Long mentorId, LocalDateTime start, LocalDateTime end) {}
