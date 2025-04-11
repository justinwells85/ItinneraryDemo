package com.anderson.ItineraryDemo.repository;

import com.anderson.ItineraryDemo.entity.Participant;
import com.anderson.ItineraryDemo.entity.Event;
import com.anderson.ItineraryDemo.entity.ParticipantEventStatus;
import com.anderson.ItineraryDemo.entity.ParticipantEventStatusId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantEventStatusRepository extends JpaRepository<ParticipantEventStatus, ParticipantEventStatusId> {
    Optional<ParticipantEventStatus> findByParticipantAndEvent(Participant participant, Event event);
}