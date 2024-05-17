import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';
import '../style.css';

const Game = () => {
    const [bird, setBird] = useState(null);
    const [options, setOptions] = useState([]);
    const [score, setScore] = useState(0);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchRandomBird();
    }, []);

    const fetchRandomBird = async () => {
        try {
            setLoading(true);
            const response = await axios.get('/api/random-bird');
            const birdData = response.data;
            setBird(birdData);
            fetchBirdOptions(birdData.imageName);
        } catch (error) {
            console.error('Error fetching random bird:', error);
            setLoading(false);
        }
    };

    const fetchBirdOptions = async (correctBirdName) => {
        try {
            const response = await axios.get('/api/bird-names');
            const birdNames = response.data;

            // Log response to check the format of birdNames
            console.log('Bird names response:', birdNames);

            // Ensure birdNames is an array
            if (Array.isArray(birdNames)) {
                const incorrectOptions = birdNames.filter(name => !name.includes(correctBirdName))
                    .sort(() => 0.5 - Math.random())
                    .slice(0, 3);

                const allOptions = [...incorrectOptions, correctBirdName].sort(() => 0.5 - Math.random());
                setOptions(allOptions);
            } else {
                console.error('Bird names is not an array:', birdNames);
            }
            setLoading(false);
        } catch (error) {
            console.error('Error fetching bird names:', error);
            setLoading(false);
        }
    };

    const handleOptionClick = (option) => {
        if (option === bird.imageName) {
            setScore(score + 1);
            Swal.fire('Correct!', 'You guessed the right bird!', 'success');
        } else {
            Swal.fire('Wrong!', 'Better luck next time!', 'error');
        }
        fetchRandomBird();
    };

    return (
        <div className="game-container">
            <h1>Bird Quiz</h1>
            <p>Score: {score}</p>
            {loading ? (
                <p>Loading...</p>
            ) : (
                bird && (
                    <div className="quiz-container">
                        <img src={`data:image/jpeg;base64,${bird.imageData}`} alt={bird.imageName} className="bird-image" />
                        <div className="options">
                            {options.map((option, index) => (
                                <button key={index} onClick={() => handleOptionClick(option)}>
                                    {option}
                                </button>
                            ))}
                        </div>
                    </div>
                )
            )}
        </div>
    );
};

export default Game;