package org.seba.services.session.impl;

import lombok.RequiredArgsConstructor;
import org.seba.entities.Mentor;
import org.seba.entities.Session;
import org.seba.entities.Student;
import org.seba.exceptions.GlobalException;
import org.seba.exceptions.user.UserNotFoundException;
import org.seba.repositories.MentorRepository;
import org.seba.repositories.SessionRepository;
import org.seba.repositories.UserRepository;
import org.seba.services.session.SessionService;
import org.seba.services.session.model.SessionInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;

    @Override
    public Session getById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "Session not found with id: " + id) {});
    }

    @Override
    public Page<Session> getByStudent(Long studentId, Pageable pageable) {
        return sessionRepository.findByStudentId(studentId, pageable);
    }

    @Override
    public Page<Session> getByMentor(Long mentorId, Pageable pageable) {
        return sessionRepository.findByMentorId(mentorId, pageable);
    }

    @Override
    public Page<Session> search(Long mentorId, Long studentId, String status, LocalDateTime afterDate, Pageable pageable) {
        return sessionRepository.searchSessions(mentorId, studentId, status, afterDate, pageable);
    }

    @Override
    public Session create(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session updateStatus(Long sessionId, String newStatus) {
        Session session = getById(sessionId);
        session.setStatus(newStatus);
        return sessionRepository.save(session);
    }

    @Override
    public void delete(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    @Override
    public Session createFromInput(SessionInput input) {
        Mentor mentor = mentorRepository.findById(input.mentorId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Mentor not found"));

        Student student = (Student) userRepository.findById(input.studentId())
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "Student not found"));

        Session session = new Session();
        session.setMentor(mentor);
        session.setStudent(student);
        session.setDate(input.date());
        session.setStatus("PENDING");

        return sessionRepository.save(session);
    }

}
