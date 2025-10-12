package com.soen343.tbd.domain.repository;

import com.soen343.tbd.domain.model.Station;
import com.soen343.tbd.domain.model.ids.StationId;

import java.util.Optional;

public interface StationRepository {
    Optional<Station> findById(StationId stationId);

    void save(Station station);
}

