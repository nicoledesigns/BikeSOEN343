package com.soen343.tbd.application.service;

import com.soen343.tbd.application.dto.OperatorRebalanceDTO;
import com.soen343.tbd.domain.model.Bike;
import com.soen343.tbd.domain.model.Dock;
import com.soen343.tbd.domain.model.Station;
import com.soen343.tbd.domain.model.enums.DockStatus;
import com.soen343.tbd.domain.model.enums.StationStatus;
import com.soen343.tbd.domain.model.ids.StationId;
import com.soen343.tbd.domain.repository.BikeRepository;
import com.soen343.tbd.domain.repository.DockRepository;
import com.soen343.tbd.domain.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.soen343.tbd.domain.model.ids.BikeId;
import com.soen343.tbd.domain.model.ids.DockId;
import com.soen343.tbd.application.dto.OperatorRebalanceDTO;
/*
 this class is for operator, 
 first making it be able to change the status active/outOFservice
 
 second make the rebalance function
 */

/*
 >> written by chatgpt but humanly checked
 by which i mean it completely ignored the functions i just made it write and decided to use setter directly so i asked it to not do that, otherwise it looks like the other classes closely enough
 update: it keeps making id into long
 update2: apparently long ids are supposed to be used bcs dto, sorry chatgpt

 trust aissues fr
 */
@Service
public class OperatorService {

    private static final Logger logger = LoggerFactory.getLogger(OperatorService.class);

    private final BikeRepository bikeRepository;
    private final DockRepository dockRepository;
    private final StationRepository stationRepository;
    
    public OperatorService(BikeRepository bikeRepository, DockRepository dockRepository, StationRepository stationRepository) {
        this.bikeRepository = bikeRepository;
        this.dockRepository = dockRepository;
        this.stationRepository = stationRepository;
    }

    // allows operator to toggle between active and out of service for a station
    public void updateStationStatus(StationId stationId, StationStatus newStatus) {
        logger.info("Operator status update for stationId: {} to {}", stationId, newStatus);

        // Fetch entity
        Station station = stationRepository.findById(stationId)
            .orElseThrow(() -> new RuntimeException("Station Not found with ID: " + stationId.value()));

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

    // Rebalance function updated to handle only IDs passed from frontend
    @Transactional
    public void rebalanceBike(OperatorRebalanceDTO dto) {
        // Step 1: Retrieve the entities using the IDs from the DTO

        // 1.1: get them ids into objects
        BikeId bikeId = new BikeId(dto.getBikeId());
        DockId sourceDockId = new DockId(dto.getSourceDockId());
        DockId targetDockId = new DockId(dto.getTargetDockId());
        StationId sourceStationId = new StationId(dto.getSourceStationId());
        StationId targetStationId = new StationId(dto.getTargetStationId());

        // 1.2: fetch the entities from the repo from ids
        Bike selectedBike = bikeRepository.findById(bikeId)
            .orElseThrow(() -> new RuntimeException("Bike not found with ID: " + bikeId.value()));
    
        Dock sourceDock = dockRepository.findById(sourceDockId)
            .orElseThrow(() -> new RuntimeException("Source dock not found with ID: " + sourceDockId.value()));
    
        Dock targetDock = dockRepository.findById(targetDockId)
            .orElseThrow(() -> new RuntimeException("Target dock not found with ID: " + targetDockId.value()));
    
        Station sourceStation = stationRepository.findById(sourceStationId)
            .orElseThrow(() -> new RuntimeException("Source station not found with ID: " + sourceStationId.value()));
    
        Station targetStation = stationRepository.findById(targetStationId)
            .orElseThrow(() -> new RuntimeException("Target station not found with ID: " + targetStationId.value()));
        
        
        // Step 2: When operator clicks 'Rebalance', update the database
        // Move bike to target dock
        selectedBike.setDockId(targetDockId);
        bikeRepository.save(selectedBike);
        logger.info("Bike {} successfully moved to target dock: {} at target station: {}", 
        selectedBike.getBikeId().value(), targetDock.getDockId().value(), targetStation.getStationId().value());

        // Update source dock to empty
        sourceDock.setStatus(DockStatus.EMPTY);
        dockRepository.save(sourceDock);
        logger.info("Source dock {} is now empty", sourceDock.getDockId().value());

        // Update target dock to occupied
        targetDock.setStatus(DockStatus.OCCUPIED);
        dockRepository.save(targetDock);
        logger.info("Target dock {} is now occupied", targetDock.getDockId().value());

        // Update the source station's bike count (decrement)
        sourceStation.decrementBikesDocked();
        stationRepository.save(sourceStation);
        logger.info("Source station {} bike count decreased", sourceStation.getStationId().value());

        // Update the target station's bike count (increment)
        targetStation.incrementBikesDocked();
        stationRepository.save(targetStation);
        logger.info("Target station {} bike count increased", targetStation.getStationId().value());
    }
}
