package org.seba.repositories;

import org.seba.entities.Session;
import org.seba.repositories.custom.SessionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>, SessionRepositoryCustom {
    List<Session> findByStudentId(Long studentId);
    List<Session> findByMentorId(Long mentorId);
    List<Session> findByMentorIdAndDateAfter(Long mentorId, LocalDateTime date);
    Page<Session> findByStudentId(Long studentId, Pageable pageable);
    Page<Session> findByMentorId(Long mentorId, Pageable pageable);
}

