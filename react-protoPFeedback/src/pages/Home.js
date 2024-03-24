import React from 'react';
import { Link } from 'react-router-dom';
import '../style.css';

function Home() {
    return (
        <>
            <div className="header">
                <nav>
                    <ul>
                        <div className="white-box-header"><Link to="/about-us">About Us</Link></div>
                    </ul>
                </nav>
            </div>

            <div className="welcome-box">
                <h2>Welcome to ProtoPNet feedback collector website!</h2>
                <p>Help us by giving feedback on the correctness of model prototypes prediction</p>

                <div className="button-container">
                    <Link className="action-button" to="/login">Log In</Link>
                    <Link className="action-button" to="/register">Sign Up</Link>
                </div>
            </div>
        </>
    );
}

export default Home;
