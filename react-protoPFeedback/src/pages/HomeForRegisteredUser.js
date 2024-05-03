import React from 'react';
import { Link } from 'react-router-dom';
import '../style.css';

function HomeForRegisteredUser() {
    return (
        <>
            <div className="header">
                <nav>
                    <ul>
                        <div className="white-box-header"><Link to="/about-us">About Us</Link></div>
                        <div className="white-box-header"><Link to="/picture-selection">Bird Selection</Link></div>
                        <div className="white-box-header sign-out"><Link to="/">Sign Out</Link></div>
                    </ul>
                </nav>
            </div>

            <div className="content-container">
                <div className="welcome-box">
                    <h2>Let's start our journey!</h2>
                    <div className="button-container">
                        <Link to="/picture-selection" className="action-button">Select birds</Link>
                    </div>
                </div>
            </div>
        </>
    );
}

export default HomeForRegisteredUser;
