package com.anderson.ItineraryDemo.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Itinerary_Group_Events")
public class ItineraryGroupEvent {

    @EmbeddedId
    private ItineraryGroupEventId id = new ItineraryGroupEventId();

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private ItineraryGroup itineraryGroup;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    // 🔧 Constructor for use in DataLoader
    public ItineraryGroupEvent(ItineraryGroup group, Event event) {
        this.itineraryGroup = group;
        this.event = event;
        this.id = new ItineraryGroupEventId(group.getGroupId(), event.getEventId());
    }

    public ItineraryGroupEvent() {}
}
