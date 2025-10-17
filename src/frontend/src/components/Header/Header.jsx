import * as React from "react";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import "./Header.css";
import logo from "../assets/logo.png";

export default function Header(props) {
  const navigate = useNavigate();
  const isLoggedIn = localStorage.getItem('jwt_token');

  const handleLogout = () => {
    try {
      localStorage.removeItem('jwt_token');
      localStorage.removeItem('user_email');
      localStorage.removeItem('user_full_name');
      localStorage.removeItem('user_role');
      delete axios.defaults.headers.common['Authorization'];
    } finally {
      navigate('/login', { replace: true });
    }
  };

  return (
    <header className="App-header">
      <img src={logo} className="App-logo" alt="logo" />
      <h1>{props.pageTitle}</h1>
      {isLoggedIn && (
        <button onClick={handleLogout} className="logout-btn">
          Logout
        </button>
      )}
    </header>
  );
}
