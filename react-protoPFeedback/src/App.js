import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import HomeForRegisteredUser from "./pages/HomeForRegisteredUser";
import AboutUs from "./pages/AboutUs";
import BirdSelection from "./pages/BirdSelection";
import Results from "./pages/Results";
import { ImageProvider } from './pages/ImageContext';

function App() {
    return (
        <ImageProvider>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} exact />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/home" element={<HomeForRegisteredUser />} />
                <Route path="/about-us" element={<AboutUs />} />
                <Route path="/picture-selection" element={<BirdSelection />} />
                <Route path="/selected-pictures" element={<Results />} />
            </Routes>
        </BrowserRouter>
        </ImageProvider>
    );
}

export default App;
