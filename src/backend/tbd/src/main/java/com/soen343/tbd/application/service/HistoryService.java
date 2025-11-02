package com.soen343.tbd.application.service;

import com.soen343.tbd.domain.model.Trip;
import com.soen343.tbd.domain.model.ids.TripId;
import com.soen343.tbd.domain.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    @Autowired
    private TripRepository tripRepository;


    // Get a trip by trip ID and user email (not used for now, as I changed my implementation decision mid-way, but keeping it in case it's needed later)

    public Optional<Trip> getTripByTripIdAndEmail(Long tripId, String email) {
        TripId domainTripId = new TripId(tripId);

        return tripRepository.findByTripIdAndEmail(domainTripId, email);
    }

     //Get all trips for a user by email

    public List<Trip> getAllTripsByEmail(String email) {

        return tripRepository.findTripByEmail(email);
    }
}
