package com.soen343.tbd.application.controller;

import com.soen343.tbd.application.service.OperatorService;
import com.soen343.tbd.domain.model.enums.StationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 CURRENT STATUS: blocked by "Unauthorized. Please login again."
 dont really know if it works and i refuse to mess with the security layer where i'll have even more trouble tracking whats happening
 */
@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    private static final Logger logger = LoggerFactory.getLogger(OperatorController.class);


    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @PutMapping("/stations/{stationId}/status")
    public ResponseEntity<?> updateStationStatus(
            @PathVariable Long stationId,
            @RequestParam StationStatus newStatus) {

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
