import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../style.css';

function Register() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
    });
    const [error, setError] = useState('');

    const { username, email, password, confirmPassword } = formData;

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            setError('Passwords do not match');
            return;
        }

        try {
            const response = await fetch('/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, email, password }),
            });

            if (response.ok) {
                navigate('/login?registrationSuccess');
            } else {
                const result = await response.json();
                setError(result.message || 'An error occurred. Please try again.');
            }
        } catch (error) {
            setError('Failed to connect to the server. Please try again.');
        }
    };

    return (
        <div className="register-container">
            <h2>Register</h2>
            {error && <p className="error">{error}</p>} {/* Display any error that occurs */}
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Username:</label>
                <input type="text" id="username" name="username" required value={username} onChange={handleChange} />
                <br/>
                <label htmlFor="email">Email:</label>
                <input type="email" id="email" name="email" required value={email} onChange={handleChange} />
                <br/>
                <label htmlFor="password">Password:</label>
                <input type="password" id="password" name="password" required value={password} onChange={handleChange} />
                <br/>
                <label htmlFor="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required value={confirmPassword} onChange={handleChange} />
                <br/>
                <button type="submit">Register</button>
            </form>
            <p>Already have an account? <a href="/src/pages/Login">Login</a></p>
        </div>
    );
}

export default Register;
