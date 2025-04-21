package org.seba.repositories;

import org.seba.entities.Mentor;
import org.seba.repositories.custom.MentorRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long>, JpaSpecificationExecutor<Mentor>, MentorRepositoryCustom {

    Optional<Mentor> findByEmail(String email);

    @Query("SELECT m FROM Mentor m JOIN m.skills s WHERE s.name = :skillName")
    List<Mentor> findBySkillName(@Param("skillName") String skillName);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.mentor.id = :mentorId")
    Double getAverageRatingByMentorId(@Param("mentorId") Long mentorId);
}

