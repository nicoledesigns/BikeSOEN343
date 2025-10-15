package com.soen343.tbd.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.soen343.tbd.domain.model.Bill;
import com.soen343.tbd.domain.model.ids.BillId;
import com.soen343.tbd.domain.model.ids.TripId;
import com.soen343.tbd.domain.model.ids.UserId;
import com.soen343.tbd.infrastructure.persistence.entity.BillEntity;

@Mapper(componentModel = "spring")
public interface BillMapper {

    // Entity -> Domain
    @Mapping(target = "billId", expression = "java(new BillId(e.getBillId()))")
    @Mapping(target = "tripId", expression = "java(new TripId(e.getTrip() != null ? e.getTrip().getTripId() : null))")
    @Mapping(target = "userId", expression = "java(new UserId(e.getUser() != null ? e.getUser().getUserId() : null))")
    @Mapping(target = "cost", source = "cost")
    Bill toDomain(BillEntity e);

    // Domain -> Entity
    @Mapping(target = "billId", expression = "java(d.getBillId() != null ? d.getBillId().value() : null)")
    @Mapping(target = "trip", ignore = true) // handled separately in adapter/service
    @Mapping(target = "user", ignore = true) // handled separately in adapter/service
    @Mapping(target = "cost", source = "cost")
    BillEntity toEntity(Bill d);
}
