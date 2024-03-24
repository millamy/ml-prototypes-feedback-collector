import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import HomeForRegisteredUser from "./pages/HomeForRegisteredUser";
import AboutUs from "./pages/AboutUs";
import BirdSelection from "./pages/BirdSelection";
import Results from "./pages/Results";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} exact />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/home" element={<HomeForRegisteredUser />} />
                <Route path="/aboutUs" element={<AboutUs />} />
                <Route path="/bird-selection" element={<BirdSelection />} />
                <Route path="/results" element={<Results />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
