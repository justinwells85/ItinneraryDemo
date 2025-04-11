package com.anderson.ItineraryDemo.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;
    private String eventName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer priority;
    @ManyToOne
    @JoinColumn(name = "successor_event_id")
    private Event successorEvent;
}