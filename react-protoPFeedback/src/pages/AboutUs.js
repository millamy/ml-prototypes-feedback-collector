import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../style.css';

function AboutUs() {
    return (
        <div>
            <div className="header">
                <nav>
                    <ul>
                        <div className="white-box-header"><a href="/home">Home</a></div>
                        <div className="white-box-header"><a href="/picture-selection">Bird Selection</a></div>
                    </ul>
                </nav>
            </div>

            <div className="content-container">
                <div className="welcome-box">
                    <h2>Who We Are</h2>
                    <p>We are a team of three technology enthusiasts from Jagiellonian University, led by Dawid Rymarczyk. We are developing a user-friendly tool to help understand the thought progress of a machine learning model. We are using a model developed by Chaofan Chen, Oscar Li, Chaofan Tao, Alina Jade Barnett, and Cynthia Rudin from <a href="https://github.com/cfchen-duke/ProtoPNet" target="_blank">https://github.com/cfchen-duke/ProtoPNet</a></p>
                    <div className="button-container">
                        <a className="action-button" href="/picture-selection">Get Started</a>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AboutUs;
