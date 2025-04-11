package com.anderson.ItineraryDemo.entity;

import lombok.Data;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ItineraryGroupEventId implements Serializable {
    private Integer groupId;
    private Integer eventId;

    public ItineraryGroupEventId() {}

    public ItineraryGroupEventId(Integer groupId, Integer eventId) {
        this.groupId = groupId;
        this.eventId = eventId;
    }
}
