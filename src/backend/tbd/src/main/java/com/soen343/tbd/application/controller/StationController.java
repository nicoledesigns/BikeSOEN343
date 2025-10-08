package com.soen343.tbd.application.controller;

import com.soen343.tbd.application.dto.StationDetailsDTO;
import com.soen343.tbd.application.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private static final Logger logger = LoggerFactory.getLogger(StationController.class);

    @Autowired
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    /**
     * Get complete station details including all docks and bikes
     *
     * Example: GET /api/stations/1/details
     *
     * Returns:
     * - Station information (name, address, capacity, etc.)
     * - List of all docks at this station
     * - For each dock, the bike (if any) that's currently docked there
     */
    @GetMapping("/{stationId}/details")
    public ResponseEntity<StationDetailsDTO> getStationDetails(@PathVariable Long stationId) {
        logger.info("Received request to get station details for stationId: {}", stationId);

        return stationService.getStationWithDetails(stationId)
                .map(stationDetails -> {
                    logger.info("Successfully retrieved station details for stationId: {}", stationId);
                    return ResponseEntity.ok(stationDetails);
                })
                .orElseGet(() -> {
                    logger.warn("Station not found for stationId: {}", stationId);
                    return ResponseEntity.notFound().build();
                });
    }
}
