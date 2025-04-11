package com.anderson.ItineraryDemo.repository;
import com.anderson.ItineraryDemo.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {}