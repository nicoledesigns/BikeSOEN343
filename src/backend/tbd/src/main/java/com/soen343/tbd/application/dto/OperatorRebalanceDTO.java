package com.soen343.tbd.application.dto;

/*
 Dto for rebalancing
 fits in with the others, as in no objects since frontend does not know objects

 >> made by chatgpt and humanly checked
 */

public class OperatorRebalanceDTO {

    private Long bikeId;
    private Long sourceDockId;
    private Long targetDockId;
    private Long sourceStationId;
    private Long targetStationId;

    // Getters and Setters
    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public Long getSourceDockId() {
        return sourceDockId;
    }

    public void setSourceDockId(Long sourceDockId) {
        this.sourceDockId = sourceDockId;
    }

    public Long getTargetDockId() {
        return targetDockId;
    }

    public void setTargetDockId(Long targetDockId) {
        this.targetDockId = targetDockId;
    }

    public Long getSourceStationId() {
        return sourceStationId;
    }

    public void setSourceStationId(Long sourceStationId) {
        this.sourceStationId = sourceStationId;
    }

    public Long getTargetStationId() {
        return targetStationId;
    }

    public void setTargetStationId(Long targetStationId) {
        this.targetStationId = targetStationId;
    }
}
