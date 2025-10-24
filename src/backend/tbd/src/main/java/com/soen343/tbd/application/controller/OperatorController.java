package com.soen343.tbd.application.controller;

import com.soen343.tbd.application.dto.StatusRequest;
import com.soen343.tbd.application.service.OperatorService;
import com.soen343.tbd.domain.model.enums.StationStatus;
import com.soen343.tbd.domain.model.ids.StationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 Allows the operator to change station status
 */
@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    private static final Logger logger = LoggerFactory.getLogger(OperatorController.class);

    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @PostMapping("/stations/status")
    public ResponseEntity<?> updateStationStatus(@RequestBody StatusRequest statusRequest) {
        StationId stationId = new StationId(statusRequest.getStationId());
        StationStatus newStatus = StationStatus.valueOf(statusRequest.getStatus());

        logger.info("Received request to change status for station {} to {}", stationId, newStatus);

        try {
            operatorService.updateStationStatus(stationId, newStatus);
            return ResponseEntity.ok("Station status updated successfully to " + newStatus);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update station status: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during station status update", e);
            return ResponseEntity.internalServerError().body("An unexpected error occurred");
        }
    }

}
