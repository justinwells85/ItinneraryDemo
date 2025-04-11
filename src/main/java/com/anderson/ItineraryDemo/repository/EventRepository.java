package com.anderson.ItineraryDemo.repository;
import com.anderson.ItineraryDemo.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {}