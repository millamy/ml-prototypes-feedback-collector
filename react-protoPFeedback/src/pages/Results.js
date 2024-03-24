import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../style.css';

const AnalysisResults = ({ match }) => {
    const [results, setResults] = useState({
        birdNames: [],
        imgdir: '',
        originalImgclass: '',
        predictedClass: '',
        error: ''
    });
    const [feedback, setFeedback] = useState(Array(10).fill(null));


    useEffect(() => {

        fetchResults();
    }, []);

    const fetchResults = async () => {

        const fetchedResults = {};
        setResults(fetchedResults);
    };

    const handleFeedbackChange = (index, value) => {
        const updatedFeedback = [...feedback];
        updatedFeedback[index] = value;
        setFeedback(updatedFeedback);
    };

    const saveFeedback = async () => {
        const feedbackData = feedback.map((f, index) => ({
            ...f,

        }));


        console.log('Saving feedback:', feedbackData);
    };

    const saveFeedbackAndRedirect = () => {
        saveFeedback();

    };

    return (
        <div>
            <div className="header">
                {/* Header content */}
            </div>
            <div className="results-container">
                {/* Results display */}
            </div>
            <div className="center-container">
                <div className="white-box">
                    <h2>Top 10 Most Activated Prototypes</h2>
                    <div className="scrollable-table">
                        <table border="1">
                            <thead>
                            <tr>
                                <th>Original Image</th>
                                <th>Prototype Image</th>
                                <th>Correctness</th>
                            </tr>
                            </thead>
                            <tbody>
                            {Array.from({ length: 10 }, (_, index) => (
                                <tr key={index}>
                                    <td>
                                        {/* Display original image */}
                                    </td>
                                    <td>
                                        {/* Display prototype image */}
                                    </td>
                                    <td>
                                        {/* Radio buttons for correctness feedback */}
                                        <label>
                                            <input
                                                type="radio"
                                                name={`correctness_${index}`}
                                                value="yes"
                                                checked={feedback[index] === 'yes'}
                                                onChange={() => handleFeedbackChange(index, 'yes')}
                                            /> Yes
                                        </label>
                                        <label>
                                            <input
                                                type="radio"
                                                name={`correctness_${index}`}
                                                value="no"
                                                checked={feedback[index] === 'no'}
                                                onChange={() => handleFeedbackChange(index, 'no')}
                                            /> No
                                        </label>
                                        <label>
                                            <input
                                                type="radio"
                                                name={`correctness_${index}`}
                                                value="idk"
                                                checked={feedback[index] === 'idk'}
                                                onChange={() => handleFeedbackChange(index, 'idk')}
                                            /> I don't know
                                        </label>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className="next-button">
                    <button onClick={saveFeedbackAndRedirect}>Save & Continue</button>
                </div>
            </div>
            {/* Display any errors */}
            {results.error && <p style={{ color: 'red' }}>{results.error}</p>}
        </div>
    );
};

export default AnalysisResults;
