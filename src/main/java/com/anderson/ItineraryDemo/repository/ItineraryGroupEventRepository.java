package com.anderson.ItineraryDemo.repository;

import com.anderson.ItineraryDemo.entity.ItineraryGroup;
import com.anderson.ItineraryDemo.entity.ItineraryGroupEvent;
import com.anderson.ItineraryDemo.entity.ItineraryGroupEventId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraryGroupEventRepository extends JpaRepository<ItineraryGroupEvent, ItineraryGroupEventId> {
    List<ItineraryGroupEvent> findByItineraryGroup(ItineraryGroup itineraryGroup);
}
