package com.anderson.ItineraryDemo.service;

import com.anderson.ItineraryDemo.entity.*;
import com.anderson.ItineraryDemo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class AssignmentService {

    @Autowired private ParticipantRepository participantRepo;
    @Autowired private EventRepository eventRepo;
    @Autowired private SessionRepository sessionRepo;
    @Autowired private AssignmentRepository assignmentRepo;
    @Autowired private ParticipantEventStatusRepository statusRepo;
    @Autowired private ParticipantItineraryGroupRepository pigRepo;
    @Autowired private ItineraryGroupEventRepository igeRepo;

    @Transactional
    public void assignSessions() {
        List<Participant> participants = participantRepo.findAll();
        for (Participant p : participants) {
            // Get participant's itinerary groups and associated events
            List<ParticipantItineraryGroup> pGroups = pigRepo.findByParticipant(p);
            for (ParticipantItineraryGroup pg : pGroups) {
                List<ItineraryGroupEvent> groupEvents = igeRepo.findByItineraryGroup(pg.getItineraryGroup());
                // Sort events by priority (lower value = higher priority)
                groupEvents.sort(Comparator.comparingInt(ge -> ge.getEvent().getPriority()));

                for (ItineraryGroupEvent ge : groupEvents) {
                    Event event = ge.getEvent();
                    ParticipantEventStatus status = statusRepo.findByParticipantAndEvent(p, event)
                            .orElse(new ParticipantEventStatus(p, event));
                    
                    // Check dependency
                    if (event.getSuccessorEvent() != null) {
                        ParticipantEventStatus depStatus = statusRepo.findByParticipantAndEvent(p, event.getSuccessorEvent())
                                .orElse(null);
                        if (depStatus == null || !depStatus.getIsScheduled()) {
                            status.setAttemptStatus(AttemptStatus.FAILED);
                            status.setErrorMessage("Dependency not scheduled: " + event.getSuccessorEvent().getEventName());
                            statusRepo.save(status);
                            continue;
                        }
                    }

                    // Find earliest available session
                    List<Session> sessions = sessionRepo.findByEvent(event);
                    sessions.sort(Comparator.comparing(Session::getStartTime));
                    Session selectedSession = null;
                    for (Session s : sessions) {
                        if (s.getCurrentCapacity() < s.getMaxCapacity() && !hasTimeConflict(p, s)) {
                            selectedSession = s;
                            break;
                        }
                    }

                    // Assign or mark failure
                    if (selectedSession != null) {
                        Assignment assignment = new Assignment();
                        assignment.setParticipant(p);
                        assignment.setSession(selectedSession);
                        assignmentRepo.save(assignment);
                        selectedSession.setCurrentCapacity(selectedSession.getCurrentCapacity() + 1);
                        sessionRepo.save(selectedSession);
                        status.setIsScheduled(true);
                        status.setAttemptStatus(AttemptStatus.SUCCESS);
                    } else {
                        status.setAttemptStatus(AttemptStatus.FAILED);
                        status.setErrorMessage("No available session");
                    }
                    statusRepo.save(status);
                }
            }
        }
    }

    private boolean hasTimeConflict(Participant p, Session newSession) {
        List<Assignment> existing = assignmentRepo.findByParticipant(p);
        for (Assignment a : existing) {
            Session s = a.getSession();
            if (newSession.getStartTime().isBefore(s.getEndTime()) && 
                newSession.getEndTime().isAfter(s.getStartTime())) {
                return true;
            }
        }
        return false;
    }
}