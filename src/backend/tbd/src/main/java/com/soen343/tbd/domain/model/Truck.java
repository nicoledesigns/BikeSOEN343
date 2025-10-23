package com.soen343.tbd.domain.model;

import com.soen343.tbd.domain.model.ids.TruckId;
import java.util.ArrayList;
import java.util.List;

/*
 Yup, i made a single truck to temporarily store the bikes somewhere
 technically infinite capacity, just loads bikes in and out
 */
public class Truck {

    private static Truck instance;          // The single truck instance

    private final TruckId truckId;
    private final int capacity;
    private final List<Bike> loadedBikes;

    private Truck(TruckId truckId, int capacity) {
        this.truckId = truckId;
        this.capacity = capacity;
        this.loadedBikes = new ArrayList<>();
    }

    /**
     * Static accessor for the singleton Truck.
     * Lazily creates the instance on first call.
     */
    public static synchronized Truck getInstance() {
        if (instance == null) {
            instance = new Truck(new TruckId(1L), 30); // Example: Truck #1 with capacity 10
        }
        return instance;
    }

    // --- Business logic methods ---

    /** Loads a bike into the truck if capacity allows. */
    public void loadBike(Bike bike) {
        if (loadedBikes.size() >= capacity) {
            throw new IllegalStateException("Truck is full! Cannot load more bikes.");
        }
        loadedBikes.add(bike);
    }

    /** Unloads a specific bike. */
    public Bike unloadBike(Bike bike) {
        if (!loadedBikes.contains(bike)) {
            throw new IllegalArgumentException("Bike not found in truck.");
        }
        loadedBikes.removeIf(b -> b.getBikeId().equals(bike.getBikeId()));
        return bike;
    }

    // --- Getters and state helpers ---
    public TruckId getTruckId() {
        return truckId;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Bike> getLoadedBikes() {
        return loadedBikes;
    }

    // @Override
    // public String toString() {
    //     return "Truck{" +
    //             "truckId=" + truckId +
    //             ", capacity=" + capacity +
    //             ", loadedBikes=" + loadedBikes.size() +
    //             '}';
    // }
}
