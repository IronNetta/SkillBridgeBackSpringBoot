package org.seba.bll.services.session;

import org.seba.dl.entities.Session;
import org.seba.bll.services.session.model.SessionInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface SessionService {

    Session getById(Long id);

    Page<Session> getByStudent(Long studentId, Pageable pageable);

    Page<Session> getByMentor(Long mentorId, Pageable pageable);

    Page<Session> search(Long mentorId, Long studentId, String status, LocalDateTime afterDate, Pageable pageable);

    Session create(Session session);

    Session updateStatus(Long sessionId, String newStatus);

    void delete(Long sessionId);

    Session createFromInput(SessionInput input);
}
