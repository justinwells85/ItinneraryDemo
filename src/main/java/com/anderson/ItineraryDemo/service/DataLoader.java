package com.anderson.ItineraryDemo.service;

import com.anderson.ItineraryDemo.entity.*;
import com.anderson.ItineraryDemo.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DataLoader {

    @Autowired private ParticipantRepository participantRepo;
    @Autowired private ItineraryGroupRepository groupRepo;
    @Autowired private EventRepository eventRepo;
    @Autowired private SessionRepository sessionRepo;
    @Autowired private ParticipantItineraryGroupRepository pigRepo;
    @Autowired private ItineraryGroupEventRepository igeRepo;

    @PostConstruct
    public void loadData() {
        Random rand = new Random();
        List<Participant> participants = new ArrayList<>();
        List<ItineraryGroup> groups = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        List<Session> sessions = new ArrayList<>();

        // 1000 Participants
        String[] roles = {"custodial", "operations", "retail"};
        for (int i = 1; i <= 1000; i++) {
            Participant p = new Participant();
            p.setName("Participant " + i);
            p.setRole(roles[rand.nextInt(roles.length)]);
            p.setAttributes("{\"experience\": " + rand.nextInt(10) + "}");
            participants.add(p);
        }
        participantRepo.saveAll(participants);

        // Itinerary Groups (Baseline + Role-Specific)
        String[] groupNames = {"Baseline Training", "Custodial Training", "Operations Training", "Retail Training"};
        for (int i = 0; i < groupNames.length; i++) {
            ItineraryGroup g = new ItineraryGroup();
            g.setGroupName(groupNames[i]);
            g.setDescription(groupNames[i] + " description");
            g.setPriority(i); // Baseline = 0 (highest), others incremental
            groups.add(g);
        }
        groupRepo.saveAll(groups);

        // Events (Baseline + Role-Specific)
        String[] eventNames = {"Orientation", "Safety", "Custodial Basics", "Ops Intro", "Retail Skills"};
        for (int i = 0; i < eventNames.length; i++) {
            Event e = new Event();
            e.setEventName(eventNames[i]);
            e.setDescription(eventNames[i] + " description");
            e.setPriority(i);
            if (i > 0 && i < 2) e.setSuccessorEvent(events.get(0)); // Safety after Orientation
            events.add(e);
        }
        eventRepo.saveAll(events);

        // Sessions (Multiple per Event)
        LocalDateTime baseTime = LocalDateTime.of(2025, 5, 1, 9, 0);
        for (Event e : events) {
            for (int i = 0; i < 3; i++) { // 3 sessions per event
                Session s = new Session();
                s.setEvent(e);
                s.setStartTime(baseTime.plusHours(i * 2));
                s.setEndTime(baseTime.plusHours(i * 2 + 1));
                s.setMaxCapacity(50 + rand.nextInt(50)); // 50-100 capacity
                sessions.add(s);
            }
        }
        sessionRepo.saveAll(sessions);

        // Link Participants to Itinerary Groups
        for (Participant p : participants) {
            ParticipantItineraryGroup pig = new ParticipantItineraryGroup();
            pig.setParticipant(p);
            pig.setItineraryGroup(groups.get(0)); // All get Baseline
            pigRepo.save(pig);
            if ("custodial".equals(p.getRole())) pig.setItineraryGroup(groups.get(1));
            else if ("operations".equals(p.getRole())) pig.setItineraryGroup(groups.get(2));
            else if ("retail".equals(p.getRole())) pig.setItineraryGroup(groups.get(3));
            pigRepo.save(pig);
        }

        // Link Itinerary Groups to Events
        igeRepo.save(new ItineraryGroupEvent(groups.get(0), events.get(0))); // Baseline -> Orientation
        igeRepo.save(new ItineraryGroupEvent(groups.get(0), events.get(1))); // Baseline -> Safety
        igeRepo.save(new ItineraryGroupEvent(groups.get(1), events.get(2))); // Custodial -> Custodial Basics
        igeRepo.save(new ItineraryGroupEvent(groups.get(2), events.get(3))); // Operations -> Ops Intro
        igeRepo.save(new ItineraryGroupEvent(groups.get(3), events.get(4))); // Retail -> Retail Skills
    }
}