package com.soen343.tbd.infrastructure.persistence.adapter;

import com.soen343.tbd.domain.model.Dock;
import com.soen343.tbd.domain.model.ids.DockId;
import com.soen343.tbd.domain.repository.DockRepository;
import com.soen343.tbd.infrastructure.persistence.entity.StationEntity;
import com.soen343.tbd.infrastructure.persistence.mapper.DockMapper;
import com.soen343.tbd.infrastructure.persistence.repository.JpaDockRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DockRepositoryAdapter implements DockRepository {
    private final JpaDockRepository jpaDockRepository;
    private final DockMapper dockMapper;
    private final EntityManager entityManager;

    public DockRepositoryAdapter(JpaDockRepository jpa, DockMapper mapper, EntityManager entityManager) {
        this.jpaDockRepository = jpa;
        this.dockMapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Dock> findById(DockId dockId) {
        return jpaDockRepository.findById(dockId.value())
                .map(dockMapper::toDomain);
    }

    @Override
    public void save(Dock dock) {
        var dockEntity = dockMapper.toEntity(dock);

        // Set the station relationship if stationId is present
        if (dock.getStationId() != null) {
            StationEntity stationReference = entityManager.getReference(StationEntity.class, dock.getStationId().value());
            dockEntity.setStation(stationReference);
        }

        jpaDockRepository.save(dockEntity);
    }
}
