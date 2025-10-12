package com.soen343.tbd.application.service;

import com.soen343.tbd.application.dto.StationDetailsDTO;
import com.soen343.tbd.domain.model.Bike;
import com.soen343.tbd.domain.model.Dock;
import com.soen343.tbd.domain.model.Station;
import com.soen343.tbd.domain.model.ids.StationId;
import com.soen343.tbd.domain.repository.BikeRepository;
import com.soen343.tbd.domain.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StationService {

    private static final Logger logger = LoggerFactory.getLogger(StationService.class);

    private final StationRepository stationRepository;
    private final BikeRepository bikeRepository;

    public StationService(StationRepository stationRepository, BikeRepository bikeRepository) {
        this.stationRepository = stationRepository;
        this.bikeRepository = bikeRepository;
    }

    /**
     * Get a complete station view with all docks and bikes
     */
    public Optional<StationDetailsDTO> getStationWithDetails(Long stationId) {
        logger.debug("Fetching station details for stationId: {}", stationId);

        Optional<Station> stationOpt = stationRepository.findById(new StationId(stationId));

        if (stationOpt.isEmpty()) {
            logger.warn("Station with id {} not found in repository", stationId);
            return Optional.empty();
        }

        Station station = stationOpt.get();
        logger.debug("Found station: {} with {} docks", station.getStationName(),
                     station.getDocks() != null ? station.getDocks().size() : 0);

        List<Dock> docks = station.getDocks() != null ? station.getDocks() : new ArrayList<>();

        // Fetch the bike (if any) for each dock in this station
        List<Bike> allBikes = new ArrayList<>();
        for (Dock dock : docks) {
            Optional<Bike> bikeInDock = bikeRepository.findByDockId(dock.getDockId());
            if (bikeInDock.isPresent()) {
                logger.debug("Found bike {} at dock {}", bikeInDock.get().getBikeId(), dock.getDockId());
                allBikes.add(bikeInDock.get());
            }
        }

        logger.info("Successfully built station details DTO for stationId: {} with {} docks and {} bikes",
                    stationId, docks.size(), allBikes.size());

        return Optional.of(new StationDetailsDTO(station, docks, allBikes));
    }
}
