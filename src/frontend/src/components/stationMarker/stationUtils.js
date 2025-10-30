// Useful functions regarding stations

export const calculateStationOccupancy = (station) => {
    if (!station.docks || station.docks.length === 0) {
        return 0;
    }

    const occupiedDocks = station.docks.filter(dock => (dock.dockStatus === "OCCUPIED")).length;
    return (occupiedDocks / station.docks.length)*100; // We want a percentage so that we can choose a color
}

export const getOccupancyLevel = (occupancy) => {
    if (occupancy === 0 || occupancy === 100) {
        return "threshold"; // Threshold
    }
    else if (occupancy < 25) {
        return "almost"; // almost empty
    }
    else if (occupancy > 85) {
        return "almost"; // almost full
    }
    else {
        return "good";
    }
}