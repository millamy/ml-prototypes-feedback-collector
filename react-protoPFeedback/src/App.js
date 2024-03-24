import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import HomeForRegisteredUser from "./pages/HomeForRegisteredUser";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} exact />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/home" element={<HomeForRegisteredUser />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
