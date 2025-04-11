package com.anderson.ItineraryDemo.repository;
import com.anderson.ItineraryDemo.entity.ItineraryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryGroupRepository extends JpaRepository<ItineraryGroup, Integer> {}