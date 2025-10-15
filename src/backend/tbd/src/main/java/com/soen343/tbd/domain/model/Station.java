package com.soen343.tbd.domain.model;

import com.soen343.tbd.domain.model.enums.StationAvailability;
import com.soen343.tbd.domain.model.enums.StationStatus;
import com.soen343.tbd.domain.model.ids.StationId;

import java.util.List;

public class Station {
    private final StationId stationId;
    private final String stationName;
    private StationAvailability stationAvailability;
    private StationStatus stationStatus;
    private final String position;
    private final String address;
    private final int capacity;     // Total number of docks at the station
    private int numberOfBikesDocked;
    private final List<Dock> docks;
    private List<Trip> startedTrips;
    private List<Trip> endedTrips;

    public Station(StationId stationId, String stationName, StationAvailability stationAvailability,
                   StationStatus stationStatus, String position, String address, int capacity,
                   int numberOfBikesDocked, List<Dock> docks, List<Trip> startedTrips, List<Trip> endedTrips) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationAvailability = stationAvailability;
        this.stationStatus = stationStatus;
        this.position = position;
        this.address = address;
        this.capacity = capacity;
        this.numberOfBikesDocked = numberOfBikesDocked;
        this.docks = docks;
        this.startedTrips = startedTrips;
        this.endedTrips = endedTrips;
    }

    public StationId getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public StationAvailability getStationAvailability() {
        return stationAvailability;
    }

    public void setStationAvailability(StationAvailability stationAvailability) {
        this.stationAvailability = stationAvailability;
    }

    public StationStatus getStationStatus() {
        return stationStatus;
    }

    public void setStationStatus(StationStatus stationStatus) {
        this.stationStatus = stationStatus;
    }

    public String getPosition() {
        return position;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfBikesDocked() {
        return numberOfBikesDocked;
    }

    public void setNumberOfBikesDocked(int numberOfBikesDocked) {
        this.numberOfBikesDocked = numberOfBikesDocked;
    }

    public List<Dock> getDocks() {
        return docks;
    }

    public List<Trip> getStartedTrips() {
        return startedTrips;
    }

    public void setStartedTrips(List<Trip> startedTrips) {
        this.startedTrips = startedTrips;
    }

    public List<Trip> getEndedTrips() {
        return endedTrips;
    }

    public void setEndedTrips(List<Trip> endedTrips) {
        this.endedTrips = endedTrips;
    }
}
