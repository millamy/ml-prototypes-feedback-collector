import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../style.css';

function AnalysisResults() {
    const navigate = useNavigate();
    const [analysisResults, setAnalysisResults] = useState(null);
    const [prototypes, setPrototypes] = useState([]);
    const [correctnessFeedback, setCorrectnessFeedback] = useState([]);

    // TO DO: implement /api/analysis-results endpoint (or fetch data from existing endpoints).
    // Rewritten by ChatGPT just to compile without errors.
    // Right now this page is not showing anything.
    //Feel free to change as you like.

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('/api/analysis-results');
                if (!response.ok) {
                    throw new Error('Failed to fetch analysis results');
                }
                const data = await response.json();
                setAnalysisResults(data);
                setPrototypes(data.prototypes || []);


                setCorrectnessFeedback(data.prototypes.map(() => ''));
            } catch (error) {
                console.error('Error fetching analysis results:', error);

            }
        };

        fetchData();
    }, []);

    const handleFeedbackChange = (index, value) => {
        const updatedFeedback = [...correctnessFeedback];
        updatedFeedback[index] = value;
        setCorrectnessFeedback(updatedFeedback);
    };

    const saveFeedbackAndContinue = async () => {
        const feedbackDataArray = prototypes.map((prototype, index) => ({
            imageClass: analysisResults.originalImgclass,
            predictedImageClass: analysisResults.predictedClass,
            originalImagePath: `/results_images/${analysisResults.imgdir}/${index + 1}/prototype`,
            prototypeImagePath: `/results_images/${analysisResults.imgdir}/${index + 1}/activated`,
            correctness: correctnessFeedback[index]
        }));

        try {
            const response = await fetch('http://localhost:8080/save-feedback', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(feedbackDataArray)
            });

            if (!response.ok) {
                throw new Error('Failed to save feedback');
            }

            const data = await response.json();
            console.log('Feedback saved successfully:', data);

            // Navigate to the next analysis page
            navigate('/analyze-next');
        } catch (error) {
            console.error('Error saving feedback:', error);
        }
    };

    if (!analysisResults) {
        // Show loading state while analysis results are being fetched
        return <div>Loading...</div>;
    }

    return (
        <div>
            <div className="header">
                <nav>
                    <ul>
                        <li className="white-box-header"><a href="/home">Home</a></li>
                        <li className="white-box-header"><a href="/about-us">About Us</a></li>
                    </ul>
                </nav>
            </div>

            <div className="results-container">
                <div className="analysis-box">
                    <h1 className="title">Analysis Results</h1>
                    <div>
                        <p className="image-info">Original Image Class: <span>{analysisResults.originalImgclass}</span></p>
                        <p className="image-info">Predicted Class: <span>{analysisResults.predictedClass}</span></p>
                    </div>
                </div>
            </div>

            <div className="center-container">
                <div className="white-box">
                    <h2>Top 10 Most Activated Prototypes</h2>
                    <div className="scrollable-table">
                        <table border="1">
                            <thead>
                            <tr>
                                <th style={{ width: '35%' }}>Original Image</th>
                                <th style={{ width: '35%' }}>Prototype Image</th>
                                <th style={{ width: '30%' }}>Correctness</th>
                            </tr>
                            </thead>
                            <tbody>
                            {prototypes.map((prototype, index) => (
                                <tr key={index}>
                                    <td>
                                        <img
                                            className="originalImagePath"
                                            src={`/results_images/${analysisResults.imgdir}/${index + 1}/prototype`}
                                            alt="Original Image"
                                        />
                                    </td>
                                    <td>
                                        <img
                                            className="prototypeImagePath"
                                            src={`/results_images/${analysisResults.imgdir}/${index + 1}/activated`}
                                            alt="Prototype Image"
                                        />
                                    </td>
                                    <td>
                                        <label>
                                            <input
                                                type="radio"
                                                name={`correctness_${index}`}
                                                value="yes"
                                                checked={correctnessFeedback[index] === 'yes'}
                                                onChange={() => handleFeedbackChange(index, 'yes')}
                                            />
                                            Yes
                                        </label>
                                        <label>
                                            <input
                                                type="radio"
                                                name={`correctness_${index}`}
                                                value="no"
                                                checked={correctnessFeedback[index] === 'no'}
                                                onChange={() => handleFeedbackChange(index, 'no')}
                                            />
                                            No
                                        </label>
                                        <label>
                                            <input
                                                type="radio"
                                                name={`correctness_${index}`}
                                                value="idk"
                                                checked={correctnessFeedback[index] === 'idk'}
                                                onChange={() => handleFeedbackChange(index, 'idk')}
                                            />
                                            I don't know
                                        </label>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className="next-button">
                    <button className="action-button" onClick={saveFeedbackAndContinue}>
                        Save & Continue
                    </button>
                </div>
            </div>

            {/* Display any errors */}
            {analysisResults.error && (
                <p style={{ color: 'red' }}>{analysisResults.error}</p>
            )}
        </div>
    );
}

export default AnalysisResults;
