package com.anderson.ItineraryDemo.entity;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "Itinerary_Groups")
public class ItineraryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;
    private String groupName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer priority;
}