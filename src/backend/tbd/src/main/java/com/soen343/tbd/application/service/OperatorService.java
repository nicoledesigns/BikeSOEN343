package com.soen343.tbd.application.service;

import com.soen343.tbd.domain.model.Station;
import com.soen343.tbd.domain.model.enums.StationStatus;
import com.soen343.tbd.domain.model.ids.StationId;
import com.soen343.tbd.domain.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.soen343.tbd.domain.model.Bike;
import com.soen343.tbd.domain.model.Dock;
import com.soen343.tbd.domain.model.enums.BikeStatus;
import com.soen343.tbd.domain.model.enums.DockStatus;
import com.soen343.tbd.domain.model.ids.BikeId;
import com.soen343.tbd.domain.model.ids.DockId;
import com.soen343.tbd.domain.model.Truck;
import com.soen343.tbd.domain.repository.BikeRepository;
import com.soen343.tbd.domain.repository.DockRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/*
 this class is for operator, 
 first making it be able to change the status active/outservice
 
 */

/*
 >> written by chatgpt but humanly checked
 by which i mean it completely ignored the functions i just made it write and decided to use setter directly so i asked it to not do that, otherwise it looks like the other classes closely enough
 update: it keeps making id into long

 trust aissues fr
 */
@Service
public class OperatorService {

    private static final Logger logger = LoggerFactory.getLogger(OperatorService.class);

    private final StationRepository stationRepository;
    
    // private final BikeRepository bikeRepository;
    // private final DockRepository dockRepository;

    public OperatorService(StationRepository stationRepository/*, BikeRepository bikeRepository, DockRepository dockRepository*/) {
        this.stationRepository = stationRepository;
        // this.bikeRepository = bikeRepository;
        // this.dockRepository = dockRepository;
    }

    public void updateStationStatus(StationId stationId, StationStatus newStatus) {
        logger.info("Operator status update for stationId: {} to {}", stationId, newStatus);

        Optional<Station> stationOpt = stationRepository.findById((stationId));

        if (stationOpt.isEmpty()) {
            logger.warn("Attempted to update status for non-existent station with ID: {}", stationId);
            throw new IllegalArgumentException("Station not found with ID: " + stationId);
        }

        Station station = stationOpt.get();

        switch (newStatus) {
            case ACTIVE -> {
                station.activateStation();
                logger.debug("Station {} activated successfully", station.getStationId());
            }
            case OUT_OF_SERVICE -> {
                station.deactivateStation();
                logger.debug("Station {} disactivated out of service", station.getStationId());
            }
            default -> {
                logger.warn("Unsupported station status: {}", newStatus);
                throw new IllegalArgumentException("Unsupported station status: " + newStatus);
            }
        }

        stationRepository.save(station);

        logger.info("Station {} successfully updated to status: {}", station.getStationId(), newStatus);
    }
/* 
    @Transactional
    public void pickupBikeFromStation(BikeId bikeId, StationId stationId) {
        logger.info("Operator picking up bike {} from station {}", bikeId, stationId);

        Bike bike = bikeRepository.findById((bikeId))
                .orElseThrow(() -> new IllegalArgumentException("Bike not found with ID: " + bikeId));

        Station station = stationRepository.findById((stationId))
                .orElseThrow(() -> new IllegalArgumentException("Station not found with ID: " + stationId));

        // Remove from dock
        Dock dock = dockRepository.findById(bike.getDockId())
                .orElseThrow(() -> new IllegalArgumentException("Dock not found for bike: " + bikeId));

        dock.setStatus(DockStatus.EMPTY);
        bike.setStatus(BikeStatus.MAINTENANCE);
        bike.setDockId(null); // no longer docked

        // Update database
        dockRepository.save(dock);
        bikeRepository.save(bike);

        // Decrement station’s docked count
        station.decrementBikesDocked();
        stationRepository.save(station);

        // Add bike to truck singleton
        Truck.getInstance().loadBike(bike);

        logger.info("Bike {} picked up successfully and placed in truck", bikeId);
    }

    @Transactional
    public void dropOffBikeAtStation(BikeId bikeId, StationId stationId, DockId dockId) {
        logger.info("Operator dropping off bike {} to station {} dock {}", bikeId, stationId, dockId);

        Bike bike = bikeRepository.findById((bikeId))
                .orElseThrow(() -> new IllegalArgumentException("Bike not found with ID: " + bikeId));

        if (bike == null) {
            throw new IllegalStateException("Bike with ID " + bike + " not found in truck.");
        }

        Dock dock = dockRepository.findById((dockId))
                .orElseThrow(() -> new IllegalArgumentException("Dock not found with ID: " + dockId));

        Station station = stationRepository.findById((stationId))
                .orElseThrow(() -> new IllegalArgumentException("Station not found with ID: " + stationId));

        if (dock.getStatus() != DockStatus.EMPTY) {
            throw new IllegalStateException("Dock " + dockId + " is not empty!");
        }

        // Update dock and bike
        dock.setStatus(DockStatus.OCCUPIED);
        bike.setStatus(BikeStatus.AVAILABLE);
        bike.setDockId(dock.getDockId());

        dockRepository.save(dock);
        bikeRepository.save(bike);

        // Update station bike count
        station.incrementBikesDocked();
        stationRepository.save(station);

        logger.info("Bike {} successfully dropped off to station {}", bike, stationId);
    }
        */
}
