package org.seba.dal.repositories;

import org.seba.dl.entities.Availability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByMentorId(Long mentorId);
    Page<Availability> findByMentorId(Long mentorId, Pageable pageable);
}