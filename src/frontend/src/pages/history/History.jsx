import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const History = () => {
    const navigate = useNavigate();
    const [allTrips, setAllTrips] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchId, setSearchId] = useState('');
    const [fromDate, setFromDate] = useState('');
    const [toDate, setToDate] = useState('');


    useEffect(() => {
        loadTrips();
    }, []);

    const loadTrips = async () => {
        setLoading(true);
        try {
            const response = await axios.post('http://localhost:8080/api/history/AllTrips', {
                userEmail: localStorage.getItem('user_email')
            });
            setAllTrips(response.data);
        } catch (err) {
            if (err.response?.status !== 404) {
                alert('Failed to load trips');
            }
        } finally {
            setLoading(false);
        }
    };

    const filterTrips = () => {
        // Validate trip ID as it should only be numbers
        if (searchId && !/^\d+$/.test(searchId)) {
            alert('Trip ID must contain only numbers');
            return [];
        }

        // Validate date range
        if (fromDate && toDate && fromDate > toDate) {
            alert("Starting date cannot be later than finishing date");
            return [];
        }

        return allTrips.filter(trip => {
            // Filter by ID
            if (searchId && !trip.tripId.toString().includes(searchId)) {
                return false;
            }

            // Filter by date
            if (fromDate || toDate) {
                if (!trip.startTime) return false;
                const tripDate = new Date(trip.startTime);

                if (fromDate && tripDate < new Date(fromDate)) return false;
                if (toDate && tripDate > new Date(toDate + 'T23:59:59')) return false;
            }

            return true;
        });
    };

    const filtered = filterTrips();

    return (
        <div style={{padding: '20px', maxWidth: '1000px'}}>
            <h1>Trip History</h1>
            <button onClick={() => navigate('/home')}>Back to Home</button>

            {/* Filters */}
            <div style={{margin: '20px 0', padding: '15px', border: '1px solid #ddd'}}>
                <h3>Search</h3>

                <div style={{marginBottom: '10px'}}>
                    <label>Trip ID: </label>
                    <input
                        type="text"
                        value={searchId}
                        onChange={(e) => setSearchId(e.target.value)}
                        placeholder="Enter ID"
                    />
                </div>

                <div style={{marginBottom: '10px'}}>
                    <label>From: </label>
                    <input
                        type="date"
                        value={fromDate}
                        onChange={(e) => setFromDate(e.target.value)}
                    />
                    <label style={{marginLeft: '10px'}}>To: </label>
                    <input
                        type="date"
                        value={toDate}
                        onChange={(e) => setToDate(e.target.value)}
                    />
                </div>

                <button onClick={() => { setSearchId(''); setFromDate(''); setToDate(''); }}>
                    Clear
                </button>
            </div>

            {/* Show the full name of the user*/}
            <h2>Trips for {localStorage.getItem('user_full_name')}</h2>

            {loading ? (
                <p>Loading...</p>
            ) : filtered.length === 0 ? (
                <p>{allTrips.length === 0 ? 'No trips found' : 'No trips match filters'}</p>
            ) : (
                <div>
                    <p>{filtered.length} trip(s) {allTrips.length !== filtered.length && `of ${allTrips.length}`}</p>
                    {filtered.map((trip) => (
                        <div key={trip.tripId} style={{margin: '10px 0', padding: '10px', border: '1px solid #ccc'}}>
                            <h3>Trip #{trip.tripId}</h3>
                            <p>Bike: {trip.bikeId || 'N/A'}</p>
                            <p>Bike Type: {trip.bikeType}</p>
                            <p>From Station: {trip.startStationId || 'N/A'}</p>
                            <p>To Station: {trip.endStationId || 'N/A'}</p>
                            <p>Start: {trip.startTime || 'N/A'}</p>
                            <p>End: {trip.endTime || 'N/A'}</p>
                            <p>Status: {trip.status || 'N/A'}</p>
                            <p>Bill: {trip.billId || 'N/A'}</p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default History;
