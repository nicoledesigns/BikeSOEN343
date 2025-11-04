package com.soen343.tbd.application.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soen343.tbd.domain.model.enums.EntityStatus;
import com.soen343.tbd.domain.model.enums.EntityType;
import com.soen343.tbd.domain.model.helpers.Event;
import com.soen343.tbd.domain.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void createEventForEntity(EntityType entityType, Long entityId, String metadataString, EntityStatus previousState, EntityStatus newState, String triggeredBy) {
        Timestamp occuredAt = Timestamp.from(Instant.now());
        Event event = new Event(entityId, entityType, metadataString, newState, occuredAt, previousState, triggeredBy);
        eventRepository.save(event);
    }
}
