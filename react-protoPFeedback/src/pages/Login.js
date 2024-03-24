import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import '../style.css';

function Login() {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                body: formData,
                credentials: 'include',
            });


            if (response.redirected) {
                navigate('/home');
            } else {
                alert('Login failed. Please check your username and password.');
            }
        } catch (error) {
            console.error('Login error:', error);
        }
    };

    useEffect(() => {
        if (localStorage.getItem('authenticated')) {
            navigate('/home');
        }
    }, [navigate]);

    return (
        <div className="login-container">
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">Username:</label>
                <input
                    type="text"
                    id="username"
                    name="username"
                    required
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <br />
                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    required
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <br />
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default Login;

