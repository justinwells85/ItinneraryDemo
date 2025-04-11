package com.anderson.ItineraryDemo.repository;

import com.anderson.ItineraryDemo.entity.Participant;
import com.anderson.ItineraryDemo.entity.ParticipantItineraryGroup;
import com.anderson.ItineraryDemo.entity.ParticipantItineraryGroupId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantItineraryGroupRepository extends JpaRepository<ParticipantItineraryGroup, ParticipantItineraryGroupId> {
    List<ParticipantItineraryGroup> findByParticipant(Participant participant);
}
