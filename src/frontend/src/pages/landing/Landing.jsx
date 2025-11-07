import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Map from '../../components/Map';
import "./Landing.css";

const Landing = () => {
  const navigate = useNavigate();
  const [plans, setPlans] = useState([]);
  const [loading, setLoading] = useState(true);
  const [stations, setStations] = useState([]);
  const [isOpen, setIsOpen] = useState(false);

  
  const planData = [
    { planType: "STANDARD", baseFee: 1.0, costPerMinute: 0.5 },
    { planType: "E-BIKE", baseFee: 3.0, costPerMinute: 0.35 }
  ];

  const planColors = {
    STANDARD: "#6C63FF",
    "E-BIKE": "#3FC1C9"
  };

  const generateDescription = (plan) => {
    switch (plan.planType) {
      case "STANDARD": return "Standard bike with no additional per-trip surcharge.";
      case "E-BIKE": return "Electric bike with faster rides and higher per-minute cost.";
      default: return "Flexible bike plan.";
    }
  };

  const calculateExampleTrip = (plan, durationMinutes = 30) => {
    return plan.baseFee + durationMinutes * plan.costPerMinute;
  };

  // Fetch stations just like Home
  useEffect(() => {
    const fetchStations = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/stations/allStations/details");
        const data = await response.json();
        setStations(data);
      } catch (err) {
        console.error("Error fetching stations:", err);
        setStations([]);
      }
    };
    fetchStations();
  }, []);

  useEffect(() => {
    setPlans(planData);
    setLoading(false);
  }, []);

  if (loading) return <p>Loading...</p>;

  return (
    <div className="guest-home-container" style={{ maxWidth: "1200px", margin: "0 auto", padding: "2rem" }}>
      <button 
        style={{ marginBottom: "1rem", padding: "0.5rem 1rem", cursor: "pointer" }}
        onClick={() => navigate("/login")}
      >
        Login or Signup →
      </button>

      <div style={{ display: "flex", gap: "2rem", flexWrap: "wrap" }}>
        <div style={{ flex: 2 }}>
          <h2>Available Stations</h2>
          {/* Pass stations + setStations so Map can refresh markers if needed */}
          <Map stations={stations} setStations={setStations} userRole="GUEST" />

          <div className="pricing-section" style={{ marginTop: "2rem" }}>
            <h1>Pricing Plans</h1>
            <p>Compare our ride options and choose the plan that suits you best.</p>

            <div className="pricing-cards">
              {plans.map((plan) => (
                <div
                  className="pricing-card"
                  key={plan.planType}
                  style={{
                    borderTop: `5px solid ${planColors[plan.planType]}`,
                    boxShadow: `0 4px 10px ${planColors[plan.planType]}55`
                  }}
                >
                  <h2 style={{ color: planColors[plan.planType] }}>{plan.planType}</h2>
                  <p>{generateDescription(plan)}</p>
                  <ul>
                    <li><strong>Base Fee:</strong> ${plan.baseFee.toFixed(2)}</li>
                    <li><strong>Cost per Minute:</strong> ${plan.costPerMinute.toFixed(2)}</li>
                  </ul>
                  <p>
                    <strong>Example 30-min trip:</strong> ${calculateExampleTrip(plan, 30).toFixed(2)}
                  </p>
                </div>
              ))}
            </div>
          </div>
        </div>

        <div style={{ flex: 1, textAlign: "left", minWidth: "250px" }}>
        <h3>About BikeShare</h3>
  <p>
    BikeShare is a low-cost, eco-friendly system that lets you pick up and drop off bikes at any station around the city.
    Explore stations, see bike availability, and learn how to ride responsibly.
  </p>
          <h2>How it works</h2>
<div className="info-section">
  <p>
    <strong>Find a bike near you:</strong><br /><br />
    Use our interactive map to see all nearby stations and available bikes, including e-bikes for faster rides. You can even reserve a bike in advance with just one click, ensuring it's ready when you arrive. ⏱️
  </p>
  <p>
    <strong>Unlock and ride:</strong><br /><br />
    Simply unlock your bike from your account, hop on, and enjoy a smooth, stress-free ride through the city. When your ride is done, return the bike to any station with available docks—no need to worry about availability, as our system will alert you if a station is full. 🚲🅿️
  </p>
  <p>
    <strong>Track, manage, and enjoy responsibly:</strong><br /><br />
    Instantly view your trip summary, duration, and charges for transparent pricing. Operators can easily monitor station occupancy, rebalance bikes, and ensure smooth operations in real-time. And of course, ride safely and sustainably while exploring the city! 💵🛠️🌿
  </p>
</div>
<div className="info-bubble-container">
      {/* Bubble button */}
      <div
        className="info-bubble-button"
        onClick={() => setIsOpen(!isOpen)}
        title="Click to view info"
      >
        ℹ
      </div>

      {/* Expandable panel */}
      {isOpen && (
        <div className="info-bubble-panel">
          <h3>Related Information</h3>
          <p>
          As a Guest, you can explore station occupancy, pricing plans, bike availability, and helpful system tips...     all without creating an account.          </p>
          <p>
          Sign up for free to reserve a bike or start riding and enjoy the full BikersDream experience!          </p>
        </div>
      )}
    </div>


        </div>
      </div>
    </div>
  );
};

export default Landing;

