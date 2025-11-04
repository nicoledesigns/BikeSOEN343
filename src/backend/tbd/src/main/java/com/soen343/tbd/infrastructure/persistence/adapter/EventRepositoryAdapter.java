package com.soen343.tbd.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.soen343.tbd.domain.model.enums.EntityType;
import com.soen343.tbd.domain.model.helpers.Event;
import com.soen343.tbd.domain.model.ids.EventId;
import com.soen343.tbd.domain.repository.EventRepository;
import com.soen343.tbd.infrastructure.persistence.mapper.EventMapper;
import com.soen343.tbd.infrastructure.persistence.repository.JpaEventRepository;

@Repository
public class EventRepositoryAdapter  implements EventRepository{
    JpaEventRepository jpaEventRepository;
    EventMapper eventMapper;

    @Override
    public List<Event> findEventsByEntityType(EntityType entityType) {
        return jpaEventRepository.findAllByEntityType(entityType)
                .stream()
                .map(eventMapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> findEventsByEntityId(Long entityId) {
        return jpaEventRepository.findAllByEntityId(entityId)
                .stream()
                .map(eventMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Event> findById(EventId eventId) {
        return jpaEventRepository.findById(eventId.value())
                .map(eventMapper::toDomain);
    }

    @Override
    public void save(Event event) {
        jpaEventRepository.save(eventMapper.toEntity(event));
    }
}
