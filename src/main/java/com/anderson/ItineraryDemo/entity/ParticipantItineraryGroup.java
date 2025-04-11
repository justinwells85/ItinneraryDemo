package com.anderson.ItineraryDemo.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Participant_Itinerary_Groups")
public class ParticipantItineraryGroup {
    @EmbeddedId
    private ParticipantItineraryGroupId id = new ParticipantItineraryGroupId();

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private ItineraryGroup itineraryGroup;
}
