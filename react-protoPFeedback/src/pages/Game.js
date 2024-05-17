import React, {useEffect, useState} from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';
import {motion, useAnimation} from 'framer-motion';
import '../style.css';
import {Link} from "react-router-dom";

const Game = () => {
    const [bird, setBird] = useState(null);
    const [options, setOptions] = useState([]);
    const [score, setScore] = useState(0);
    const [loading, setLoading] = useState(true);
    const [correctAnswer, setCorrectAnswer] = useState('');
    const [showScoreIncrement, setShowScoreIncrement] = useState(false);

    const controls = useAnimation();

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

    const handleOptionClick = async (option) => {
        if (option === bird.imageName) {
            setScore(score + 1);
            setShowScoreIncrement(true);
            setTimeout(() => setShowScoreIncrement(false), 1000);

            await Swal.fire({
                title: 'Correct!',
                text: 'You guessed the right bird!',
                icon: 'success',
                popup: 'success-popup',
                didClose: () => {
                    fetchRandomBird();
                }
            });
        } else {
            setCorrectAnswer(bird.imageName);
            await controls.start({
                x: [0, -10, 10, -10, 10, 0],
                transition: {duration: 0.4},
            });
            await Swal.fire({
                title: 'Wrong!',
                html: `The correct answer is: <b>${bird.imageName}</b>`,
                icon: 'error',
                popup: 'error-popup',
                didClose: () => {
                    fetchRandomBird();
                }
            });
        }
    };

    return (
        <div>
            <div className="header">
                <nav>
                    <ul>
                        <div className="white-box-header"><a href="/home">Home</a></div>
                        <div className="white-box-header"><a href="/about-us">About Us</a></div>
                        <div className="white-box-header"><Link to="/picture-selection">Bird Selection</Link></div>
                        <div className="white-box-header sign-out"><Link to="/">Sign Out</Link></div>
                    </ul>
                </nav>
            </div>
            <div className="game-container">
            <motion.h1
                    initial={{y: -100}}
                    animate={{y: 0}}
                    transition={{duration: 0.5}}
                >
                    Bird Quiz
                </motion.h1>
                <motion.p
                    initial={{opacity: 0}}
                    animate={{opacity: 1}}
                    transition={{duration: 1}}
                >
                    Score: {score}
                    {showScoreIncrement && (
                        <motion.span
                            initial={{opacity: 0, y: -20}}
                            animate={{opacity: 1, y: -40}}
                            exit={{opacity: 0, y: -60}}
                            transition={{duration: 0.5}}
                            className="score-increment"
                        >
                            +1
                        </motion.span>
                    )}
                </motion.p>
                {loading ? (
                    <p>Loading...</p>
                ) : (
                    bird && (
                        <div className="quiz-container">
                            <motion.img
                                src={`data:image/jpeg;base64,${bird.imageData}`}
                                alt={bird.imageName}
                                className="bird-image"
                                initial={{scale: 0}}
                                animate={{scale: 1}}
                                transition={{duration: 0.5}}
                            />
                            <div className="options">
                                {options.map((option, index) => (
                                    <motion.button
                                        key={index}
                                        onClick={() => handleOptionClick(option)}
                                        whileHover={{scale: 1.1}}
                                        whileTap={{scale: 0.9}}
                                        animate={option === correctAnswer ? controls : {opacity: 1}}
                                        initial={{opacity: 0}}
                                        transition={{duration: 0.5, delay: index * 0.1}}
                                    >
                                        {option}
                                    </motion.button>
                                ))}
                            </div>
                        </div>
                    )
                )}
            </div>
        </div>
    );
};

export default Game;