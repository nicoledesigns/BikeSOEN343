package com.soen343.tbd.application.observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.soen343.tbd.application.dto.DockUpdateContextDTO;
import com.soen343.tbd.application.dto.StationDetailsDTO;
import com.soen343.tbd.application.dto.StationDetailsDTO.DockWithBikeDTO;
import com.soen343.tbd.application.service.TripService;

// Concrete Subject 
@Service
public class StationPublisher implements StationSubject {
    private static final Logger logger = LoggerFactory.getLogger(StationPublisher.class);
    private final List<StationObserver> observers = new ArrayList<>();

    @Override
    public void attach(StationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(StationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(StationDetailsDTO station) {
        logger.debug("Notifying {} observers about station update", observers.size());
        // Notify all observers
        for (StationObserver observer : observers) {
            try {
                observer.update(station);
            } catch (Exception e) {
                logger.error("Error notifying observer: " + e.getMessage());
            }
        }
    }
}