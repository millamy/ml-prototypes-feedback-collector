import React from 'react';
import { Link } from 'react-router-dom';
import '../style.css';

function Menu() {
    return (
        <div>
            <nav>
                <ul>
                    <li><Link to="/home">Home</Link></li>
                    <li><Link to="/picture-selection">Birds</Link></li>
                    <li><Link to="/login">Login</Link></li>
                    <li><Link to="/register">Register</Link></li>
                </ul>
            </nav>
            <hr/>
        </div>
    );
}

export default Menu;
