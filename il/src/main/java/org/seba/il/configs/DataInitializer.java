package org.seba.il.configs;

import lombok.RequiredArgsConstructor;
import org.seba.dal.repositories.*;
import org.seba.dl.entities.*;
import org.seba.dl.enums.UserRole;
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

            // 📘 Compétences
            Skill java = skillRepository.save(new Skill("Java"));
            Skill spring = skillRepository.save(new Skill("Spring Boot"));
            Skill angular = skillRepository.save(new Skill("Angular"));
            Skill docker = skillRepository.save(new Skill("Docker"));
            Skill react = skillRepository.save(new Skill("React"));

            // 👤 Admin
            User admin = new User("admin", "admin@skillbridge.com", passwordEncoder.encode("admin123"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);

            // 👨‍🏫 Mentors
            for (int i = 1; i <= 3; i++) {
                Mentor mentor = new Mentor();
                mentor.setUsername("mentor" + i);
                mentor.setEmail("mentor" + i + "@skillbridge.com");
                mentor.setPassword(passwordEncoder.encode("mentor123"));
                mentor.setRole(UserRole.MENTOR);
                mentor.setBio("Mentor " + i + " - Expert en " + (i % 2 == 0 ? "Angular" : "Java/Spring"));
                mentor.setSkills(List.of(java, spring, i % 2 == 0 ? angular : docker));
                mentorRepository.save(mentor);

                // Disponibilités
                for (int j = 0; j < 3; j++) {
                    Availability availability = new Availability();
                    availability.setMentor(mentor);
                    availability.setStartTime(LocalDateTime.now().plusDays(j + 1).withHour(9));
                    availability.setEndTime(LocalDateTime.now().plusDays(j + 1).withHour(10));
                    availabilityRepository.save(availability);
                }
            }

            // 👩‍🎓 Étudiants
            for (int i = 1; i <= 5; i++) {
                Student student = new Student();
                student.setUsername("student" + i);
                student.setEmail("student" + i + "@skillbridge.com");
                student.setPassword(passwordEncoder.encode("student123"));
                student.setRole(UserRole.STUDENT);
                studentRepository.save(student);
            }

            List<Mentor> allMentors = mentorRepository.findAll();
            List<Student> allStudents = studentRepository.findAll();

            // 📆 Sessions & 💬 Reviews
            for (int i = 0; i < 10; i++) {
                Mentor mentor = allMentors.get(i % allMentors.size());
                Student student = allStudents.get(i % allStudents.size());

                Session session = new Session();
                session.setMentor(mentor);
                session.setStudent(student);
                session.setDate(LocalDateTime.now().plusDays(1 + i));
                session.setStatus(i % 2 == 0 ? "CONFIRMED" : "PENDING");
                sessionRepository.save(session);

                if (i % 2 == 0) {
                    Review review = new Review();
                    review.setMentor(mentor);
                    review.setAuthor(student);
                    review.setRating(3 + (i % 3)); // 3 à 5
                    review.setComment("Feedback de student" + student.getUsername());
                    reviewRepository.save(review);
                }
            }

            System.out.println("✅ Données de test insérées !");
        };
    }
}