package org.seba.bll.services.review.model;

public record ReviewInput(Long mentorId, Long studentId, int rating, String comment) {}