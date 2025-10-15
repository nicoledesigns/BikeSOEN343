package com.soen343.tbd.infrastructure.persistence.mapper;

import com.soen343.tbd.domain.model.Dock;
import com.soen343.tbd.domain.model.Station;
import com.soen343.tbd.domain.model.ids.StationId;
import com.soen343.tbd.infrastructure.persistence.entity.DockEntity;
import com.soen343.tbd.infrastructure.persistence.entity.StationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DockMapper.class, TripMapper.class})
public interface StationMapper {

    // Entity to Domain
    @Mapping(target = "stationId", expression = "java(new StationId(e.getStationId()))")
    Station toDomain(StationEntity e);

    // Domain to Entity
    @Mapping(target = "stationId", expression = "java(d.getStationId() != null ? d.getStationId().value() : null)")
    StationEntity toEntity(Station d);

    List<Dock> toDomainDockList(List<DockEntity> dockEntityList);

    List<DockEntity> toEntityDockList(List<Dock> dockDomainList);
}
