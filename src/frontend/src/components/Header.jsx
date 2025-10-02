import * as React  from "react";
import logo from './assets/logo.png'; 
import './Header.css';

export default function Header(props) {
    return (
        <header className="App-header">
            <img src={logo} className="App-logo" alt="Bikers' Dream logo" />
            <h1>{props.pageTitle}</h1>
        </header>
    );
}