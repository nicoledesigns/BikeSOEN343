import L from 'leaflet';

// Color for each level
const getColorByOccupancy = (occupancyLevel) => {
    switch (occupancyLevel) {
        case 'threshold':
            return '#ef4444';
        case 'almost':
            return '#e3b839ff';
        case 'good':
            return '#22c55e';
        default:
            return '#848484ff'; // Gray - unknown
  }
}

const createCustomIconSVG = (color) => {
  // Create a simple SVG marker
  return `data:image/svg+xml;base64,${btoa(`
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="${color}">
      <path d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5a2.5 2.5 0 0 1 0-5 2.5 2.5 0 0 1 0 5z"/>
    </svg>
  `)}`;
};

// Custom markers for each occupancy leve;
export const createStationIcon = (occupancyLevel) =>{
    const color = getColorByOccupancy(occupancyLevel);
  
  return new L.Icon({
    iconUrl: createCustomIconSVG(color),
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
  });
}
