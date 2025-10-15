package com.soen343.tbd.domain.repository;

import java.util.Optional;

import com.soen343.tbd.domain.model.Trip;
import com.soen343.tbd.domain.model.ids.TripId;

public interface TripRepository {
    Optional<Trip> findById(TripId tripId);

    void save(Trip trip);
}
