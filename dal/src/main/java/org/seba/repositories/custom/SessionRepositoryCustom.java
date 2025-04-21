package org.seba.repositories.custom;

import org.seba.entities.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface SessionRepositoryCustom {
    Page<Session> searchSessions(Long mentorId, Long studentId, String status, LocalDateTime afterDate, Pageable pageable);
}
