package com.soen343.tbd.infrastructure.persistence.mapper;

import com.soen343.tbd.domain.model.Trip;
import com.soen343.tbd.domain.model.enums.TripStatus;
import com.soen343.tbd.domain.model.ids.*;
import com.soen343.tbd.infrastructure.persistence.entity.TripEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TripMapper {

    // ---------------------------
    // Entity → Domain
    // ---------------------------
    @Mappings({
        @Mapping(target = "tripId", expression = "java(new TripId(e.getId()))"),
        @Mapping(target = "status", source = "e.status"),
        @Mapping(target = "bikeId", expression = "java(e.getBike() != null ? new BikeId(e.getBike().getBikeId()) : null)"),
        @Mapping(target = "userId", expression = "java(e.getUser() != null ? new UserId(e.getUser().getId()) : null)"),
        @Mapping(target = "startStationId", expression = "java(e.getStartStation() != null ? new StationId(e.getStartStation().getStationId()) : null)"),
        @Mapping(target = "endStationId", expression = "java(e.getEndStation() != null ? new StationId(e.getEndStation().getStationId()) : null)"),
        @Mapping(target = "startTime", source = "e.startTime"),
        @Mapping(target = "endTime", source = "e.endTime"),
        @Mapping(target = "billId", expression = "java(e.getBill() != null ? new BillId(e.getBill().getBillId()) : null)")
    })
    Trip toDomain(TripEntity e);


    // ---------------------------
    // Domain → Entity
    // ---------------------------
    @Mappings({
        @Mapping(target = "id", expression = "java(d.getTripId() != null ? d.getTripId().value() : null)"),
        @Mapping(target = "status", source = "d.status"),
        @Mapping(target = "bike", ignore = true),          // handled separately in adapter/service
        @Mapping(target = "user", ignore = true),          // handled separately in adapter/service
        @Mapping(target = "startStation", ignore = true),  // handled separately
        @Mapping(target = "endStation", ignore = true),    // handled separately
        @Mapping(target = "startTime", source = "d.startTime"),
        @Mapping(target = "endTime", source = "d.endTime"),
        @Mapping(target = "bill", ignore = true)           // Bill is created after trip completion
    })
    TripEntity toEntity(Trip d);
}
