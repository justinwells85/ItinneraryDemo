package com.anderson.ItineraryDemo.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer participantId;
    private String name;
    private String role;
    @Column(columnDefinition = "TEXT")
    private String attributes;
}