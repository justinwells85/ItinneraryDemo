package com.anderson.ItineraryDemo.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class ParticipantItineraryGroupId implements Serializable {
    private Integer participantId;
    private Integer groupId;
}
