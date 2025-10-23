import React, { useState } from "react";

function TruckArea({ truckBikes, availableDocks, onPickupBike, onDropoffBike }) {
  const [selectedDockId, setSelectedDockId] = useState("");
  const [haulBikeId, setHaulBikeId] = useState("");
  const [haulStationId, setHaulStationId] = useState("");

  // Handle hauling bike onto truck (pickup)
  const handleHaulSubmit = (e) => {
    e.preventDefault();
    if (!haulBikeId.trim() || !haulStationId.trim()) {
      alert("Please fill in both Bike ID and Station ID.");
      return;
    }
    onPickupBike(haulBikeId.trim(), haulStationId.trim());
    setHaulBikeId("");
    setHaulStationId("");
  };

  // Handle settling a random bike from the truck onto selected dock
  const handleSettleBike = () => {
    if (truckBikes.length === 0) {
      alert("No bikes on the truck to settle!");
      return;
    }
    if (!selectedDockId) {
      alert("Please select a dock to settle a bike.");
      return;
    }
    // Pick random bike
    const randomBike = truckBikes[Math.floor(Math.random() * truckBikes.length)];
    onDropoffBike(randomBike, selectedDockId);
  };

  return (
    <div
      style={{
        border: "2px solid black",
        borderRadius: "8px",
        padding: "1em",
        width: "280px",
        minHeight: "300px",
        backgroundColor: "#f9f9f9",
        marginLeft: "1em",
        fontFamily: "Arial, sans-serif",
      }}
    >
      <h3 style={{ marginBottom: "0.5em" }}>Truck</h3>

      {/* Bikes on truck */}
      <div
        style={{
          display: "flex",
          flexWrap: "wrap",
          gap: "8px",
          minHeight: "50px",
          alignItems: "center",
        }}
      >
        {truckBikes.length === 0 ? (
          <p style={{ fontStyle: "italic", color: "#666" }}>No bikes in truck</p>
        ) : (
          truckBikes.map((bike) => (
            <div
              key={bike.bikeId}
              title={`Bike ID: ${bike.bikeId}`}
              style={{
                fontSize: "1.5em",
                cursor: "default",
                userSelect: "none",
                padding: "4px",
                borderRadius: "4px",
                backgroundColor: "#d0f0fd",
                boxShadow: "0 0 2px rgba(0,0,0,0.2)",
              }}
            >
              🚲
            </div>
          ))
        )}
      </div>

      {/* Dock selector */}
      <div style={{ marginTop: "1em" }}>
        <label htmlFor="dockSelect" style={{ fontWeight: "bold" }}>
          Select dock to settle bike:
        </label>
        <select
          id="dockSelect"
          value={selectedDockId}
          onChange={(e) => setSelectedDockId(e.target.value)}
          style={{ marginLeft: "0.5em", width: "100%", marginTop: "0.3em" }}
        >
          <option value="">-- Select Dock --</option>
          {availableDocks.map((dock) => (
            <option key={dock.dockId} value={dock.dockId}>
              Dock {dock.dockId} at Station {dock.stationId}
            </option>
          ))}
        </select>

        <button
          onClick={handleSettleBike}
          disabled={truckBikes.length === 0 || !selectedDockId}
          style={{
            marginTop: "0.5em",
            width: "100%",
            padding: "0.4em",
            backgroundColor:
              truckBikes.length === 0 || !selectedDockId ? "#ccc" : "#4CAF50",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor:
              truckBikes.length === 0 || !selectedDockId ? "not-allowed" : "pointer",
          }}
        >
          Settle Bike
        </button>
      </div>

      {/* Haul bike onto truck form */}
      <form onSubmit={handleHaulSubmit} style={{ marginTop: "1.5em" }}>
        <h4 style={{ marginBottom: "0.5em" }}>Haul Bike Onto Truck</h4>

        <input
          type="text"
          placeholder="Bike ID"
          value={haulBikeId}
          onChange={(e) => setHaulBikeId(e.target.value)}
          style={{
            width: "100%",
            padding: "0.4em",
            marginBottom: "0.5em",
            borderRadius: "4px",
            border: "1px solid #ccc",
            boxSizing: "border-box",
          }}
          required
        />
        <input
          type="text"
          placeholder="Station ID"
          value={haulStationId}
          onChange={(e) => setHaulStationId(e.target.value)}
          style={{
            width: "100%",
            padding: "0.4em",
            marginBottom: "0.7em",
            borderRadius: "4px",
            border: "1px solid #ccc",
            boxSizing: "border-box",
          }}
          required
        />
        <button
          type="submit"
          style={{
            width: "100%",
            padding: "0.5em",
            backgroundColor: "#2196F3",
            color: "white",
            border: "none",
            borderRadius: "4px",
            cursor: "pointer",
          }}
        >
          Haul Bike
        </button>
      </form>
    </div>
  );
}

export default TruckArea;

