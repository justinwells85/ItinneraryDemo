package com.anderson.ItineraryDemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor // 👈 This is the key part
@NoArgsConstructor
public class ParticipantEventStatusId implements Serializable {
    private Integer participantId;
    private Integer eventId;
}
