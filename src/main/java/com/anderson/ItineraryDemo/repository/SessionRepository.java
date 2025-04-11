package com.anderson.ItineraryDemo.repository;

import com.anderson.ItineraryDemo.entity.Event;
import com.anderson.ItineraryDemo.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByEvent(Event event); 
}
