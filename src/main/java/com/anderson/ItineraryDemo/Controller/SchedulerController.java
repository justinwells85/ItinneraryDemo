package com.anderson.ItineraryDemo.controller;

import com.anderson.ItineraryDemo.entity.Assignment;
import com.anderson.ItineraryDemo.entity.Participant;
import com.anderson.ItineraryDemo.repository.AssignmentRepository;
import com.anderson.ItineraryDemo.repository.ParticipantRepository;
import com.anderson.ItineraryDemo.service.AssignmentService;
import com.anderson.ItineraryDemo.service.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SchedulerController {

    @Autowired private AssignmentService assignmentService;
    @Autowired private DataLoader dataLoader;
    @Autowired private ParticipantRepository participantRepo;
    @Autowired private AssignmentRepository assignmentRepo;

    @PostMapping("/assign")
    public ResponseEntity<String> triggerAssignments() {
        assignmentService.assignSessions();
        return ResponseEntity.ok("Assignment process completed");
    }

    @GetMapping("/schedules/{participantId}")
    public ResponseEntity<List<Assignment>> getSchedule(@PathVariable Integer participantId) {
        Participant participant = participantRepo.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found"));
        List<Assignment> assignments = assignmentRepo.findByParticipant(participant);
        return ResponseEntity.ok(assignments);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetData() {
        // Clear existing data
        assignmentRepo.deleteAll();
        participantRepo.deleteAll();
        // Reload mock data
        dataLoader.loadData();
        return ResponseEntity.ok("Data reset and reloaded");
    }
}