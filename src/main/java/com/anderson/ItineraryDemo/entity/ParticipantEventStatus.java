package com.anderson.ItineraryDemo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Participant_Event_Status")
public class ParticipantEventStatus {

    @EmbeddedId
    private ParticipantEventStatusId id = new ParticipantEventStatusId();

    @ManyToOne
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    private Boolean isScheduled = false;

    @Enumerated(EnumType.STRING)
    private AttemptStatus attemptStatus = AttemptStatus.NOT_ATTEMPTED;

    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    // Custom constructor for use in services or DataLoader
    public ParticipantEventStatus(Participant participant, Event event) {
        this.participant = participant;
        this.event = event;
        this.id = new ParticipantEventStatusId(
                participant.getParticipantId(),
                event.getEventId()
        );
    }

    // No-arg constructor required by JPA
    public ParticipantEventStatus() {}
}
