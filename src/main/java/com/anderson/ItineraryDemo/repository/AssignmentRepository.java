package com.anderson.ItineraryDemo.repository;

import com.anderson.ItineraryDemo.entity.Assignment;
import com.anderson.ItineraryDemo.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {
    List<Assignment> findByParticipant(Participant participant);
}