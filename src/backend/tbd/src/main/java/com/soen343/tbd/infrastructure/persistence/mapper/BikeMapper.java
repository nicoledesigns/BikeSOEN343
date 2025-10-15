package com.soen343.tbd.infrastructure.persistence.mapper;

import com.soen343.tbd.domain.model.Bike;
import com.soen343.tbd.domain.model.ids.BikeId;
import com.soen343.tbd.domain.model.ids.DockId;
import com.soen343.tbd.infrastructure.persistence.entity.BikeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { TripMapper.class })
public interface BikeMapper {

    //Entity to Domain
    @Mapping(target = "bikeId", expression = "java(new BikeId(e.getBikeId()))")
    @Mapping(target = "dockId", expression = "java(e.getDock() != null ? new DockId(e.getDock().getDockId()) : null)")
    Bike toDomain(BikeEntity e);

    // Domain to Entity - dock relationship is handled separately in the adapter
    @Mapping(target = "bikeId", expression = "java(d.getBikeId() != null ? d.getBikeId().value() : null)")
    @Mapping(target = "dock", ignore = true)
    @Mapping(target = "trips", ignore = true)
    BikeEntity toEntity(Bike d);
}
