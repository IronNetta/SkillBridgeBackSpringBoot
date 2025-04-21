package org.seba.configs;

import lombok.RequiredArgsConstructor;
import org.seba.entities.*;
import org.seba.enums.UserRole;
import org.seba.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;
    private final SessionRepository sessionRepository;
    private final ReviewRepository reviewRepository;
    private final AvailabilityRepository availabilityRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (userRepository.count() > 0) return;

            Skill java = skillRepository.save(new Skill("Java"));
            Skill spring = skillRepository.save(new Skill("Spring Boot"));
            Skill angular = skillRepository.save(new Skill("Angular"));

            Mentor mentor = new Mentor();
            mentor.setUsername("mentor");
            mentor.setEmail("mentor@skillbridge.com");
            mentor.setPassword(passwordEncoder.encode("mentor123"));
            mentor.setRole(UserRole.MENTOR);
            mentor.setBio("Développeur fullstack senior");
            mentor.setSkills(List.of(java, spring));
            mentorRepository.save(mentor);

            Student student = new Student();
            student.setUsername("student");
            student.setEmail("student@skillbridge.com");
            student.setPassword(passwordEncoder.encode("student123"));
            student.setRole(UserRole.STUDENT);
            studentRepository.save(student);

            User admin = new User("admin", "admin@skillbridge.com", passwordEncoder.encode("admin123"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);

            Availability a1 = new Availability();
            a1.setMentor(mentor);
            a1.setStartTime(LocalDateTime.now().plusDays(1).withHour(10));
            a1.setEndTime(LocalDateTime.now().plusDays(1).withHour(11));
            availabilityRepository.save(a1);

            Session s = new Session();
            s.setMentor(mentor);
            s.setStudent(student);
            s.setDate(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30));
            s.setStatus("PENDING");
            sessionRepository.save(s);

            Review r = new Review();
            r.setMentor(mentor);
            r.setAuthor(student);
            r.setRating(5);
            r.setComment("Super mentor !");
            reviewRepository.save(r);
        };
    }
}

