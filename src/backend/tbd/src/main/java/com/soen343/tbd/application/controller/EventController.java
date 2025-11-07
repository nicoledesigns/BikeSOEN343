package com.soen343.tbd.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.soen343.tbd.application.observer.SSEStationObserver;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final SSEStationObserver sseStationObserver;

    public EventController(SSEStationObserver sseStationObserver) {
        this.sseStationObserver = sseStationObserver;
    }

    @GetMapping("/subscribe")
    public SseEmitter subscribeToEvents() {
        return sseStationObserver.subscribe(); // same method as in SSEStationObserver.java
    }

}
