package com.soen343.tbd.application.service;

import com.soen343.tbd.domain.model.Station;
import com.soen343.tbd.domain.model.enums.StationStatus;
import com.soen343.tbd.domain.model.ids.StationId;
import com.soen343.tbd.domain.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 this class is for operator, 
 first making it be able to change the status active/outservice
 
 */

/*
 >> written by chatgpt but humanly checked
 by which i mean it completely ignored the functions i just made it write and decided to use setter directly so i asked it to not do that, otherwise it looks like the other classes closely enough

 trust aissues fr
 */
@Service
public class OperatorService {

    private static final Logger logger = LoggerFactory.getLogger(OperatorService.class);

    private final StationRepository stationRepository;

    public OperatorService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public void updateStationStatus(Long stationId, StationStatus newStatus) {
        logger.info("Operator status update for stationId: {} to {}", stationId, newStatus);

        Optional<Station> stationOpt = stationRepository.findById(new StationId(stationId));

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

        logger.info("Station {} successfully updated to status: {}", station.getStationId(), newStatus);
    }
}
