import './App.css';
import Auth from './pages/auth/Auth.jsx';
import Home from './pages/home/Home.jsx';
import Billing from './pages/billing/Billing.jsx';
import History from './pages/history/History.jsx';
import ProtectedRoute from './ProtectedRoute.jsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Pricing from './pages/pricing/Pricing.jsx';
import Landing from './pages/landing/Landing.jsx';


function App() {
  return (
    <Router>
      <Routes>
        {/* Landing page - shown on startup */}
        <Route path="/" element={<Landing />} />
        <Route path="/landing" element={<Landing />} />

        {/* Pricing page - only pricing cards */}
        <Route path="/pricing" element={<Pricing />} />

        {/* Auth routes */}
        <Route path="/login" element={<Auth />} />
        <Route path="/auth" element={<Auth />} />

        {/* Protected routes */}
        <Route
          path="/home"
          element={
            // Wrap Home component in ProtectedRoute to enforce authentication, so only authenticated users can access it
            <ProtectedRoute>
              <Home />
            </ProtectedRoute>
          }
        />
        <Route
          path="/history"
          element={
            <ProtectedRoute>
              <History />
            </ProtectedRoute>
          }
        />
        <Route
          path="/billing"
          element={
            <ProtectedRoute>
              <Billing />
            </ProtectedRoute>
          }
        />

        {/* Redirect any unknown routes to landing */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Router>
  );
}

export default App;
