import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import '../style.css';

function Results({ birdNames, originalImgclass, predictedClass, imgdir, error }) {
    const [feedbackDataArray, setFeedbackDataArray] = useState([]);
    console.log('imgdir:', imgdir);
    console.log('birdNames:', birdNames);
    console.log('originalImgclass:', originalImgclass);

    const saveFeedback = () => {
        const feedbackDataArray = [];

        // Collect feedback data from radio buttons
        for (let index = 1; index <= 10; index++) {
            const selectedValue = document.querySelector(`input[name="correctness_${index}"]:checked`);
            if (selectedValue) {
                const feedbackData = {};
                feedbackData['imageClass'] = document.getElementById("originalImageClass").innerText;
                feedbackData['predictedImageClass'] = document.getElementById("predictedImageClass").innerText;
                const thSrc = document.getElementsByClassName("originalImagePath")[index - 1].getAttribute("src");

                let parts = thSrc.split("/");

                let imgdir = parts[2]; // ex. "037.Acadian_Flycatcher"
                let pathIndex = parts[3]; // ex. "1"

                feedbackData['originalImagePath'] = `${imgdir}/vgg19/001/100_0push0.7411.pth/most_activated_prototypes/prototype_activation_map_by_top-${pathIndex}_prototype.png`;

                feedbackData['prototypeImagePath'] = `${imgdir}/vgg19/001/100_0push0.7411.pth/most_activated_prototypes/top-${pathIndex}_activated_prototype_self_act.png`;

                feedbackData['correctness'] = selectedValue.value;
                feedbackDataArray.push(feedbackData);
            }
        }

        console.log(feedbackDataArray);

        // Convert feedback data to CSV format
        const csvRows = [];
        const headers = ["imageClass", "predictedImageClass", "originalImagePath", "prototypeImagePath", "correctness"];
        csvRows.push(headers.join(','));

        feedbackDataArray.forEach(feedbackData => {
            const values = headers.map(header => feedbackData[header]);
            csvRows.push(values.join(','));
        });

        const csvContent = csvRows.join('\n');
        const blob = new Blob([csvContent], { type: 'text/csv' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.setAttribute('hidden', '');
        a.setAttribute('href', url);
        a.setAttribute('download', 'feedback_data.csv');
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    };

    return (
        <body>
            <div className="header">
                <nav>
                    <ul>
                        <div className="white-box-header"><a href="/home">Home</a></div>
                        <div className="white-box-header"><a href="/about-us">About Us</a></div>
                    </ul>
                </nav>
            </div>
            <div className="results-container">
                <div d="analysis-results" className="analysis-box">
                    <h1 className="title">Analysis Results</h1>
                    {birdNames && (
                        <div>
                            <p className="image-info">Original Image Class: <span id="originalImageClass">{birdNames[originalImgclass - 1].replace(/^\d{3}\./, '').replace(/_/g, ' ')}</span></p>
                            <p className="image-info">Predicted Class: <span id="predictedImageClass">{birdNames[predictedClass].replace(/^\d{3}\./, '').replace(/_/g, ' ')}</span></p>
                        </div>
                    )}
                </div>
            </div>

            <div className="center-container">
                <div id="white-results-box" className="white-box">
                    <h2>Top 10 Most Activated Prototypes</h2>
                    <div className="scrollable-table">
                        <table className="results-table">
                            <thead>
                            <tr>
                                <th style={{ width: '35%' }}>Original Image</th>
                                <th style={{ width: '35%' }}>Prototype Image</th>
                                <th style={{ width: '30%' }}>Correctness</th>
                            </tr>
                            </thead>
                            <tbody>
                            {Array.from({ length: 10 }, (_, index) => (
                                <tr key={index}>
                                    <td>
                                        <img className="originalImagePath"
                                             src={`/results_images/${imgdir}/${index + 1}/prototype`}
                                             alt="Original Image"/>
                                    </td>
                                    <td>
                                        <img className="prototypeImagePath"
                                             src={`/results_images/${imgdir}/${index + 1}/activated`}
                                             alt="Prototype Image"/>
                                    </td>
                                    <td>
                                        <div className="radio-group">
                                            <label><input type="radio" name={`correctness_${index + 1}`}
                                                          value="yes"/> Yes</label>
                                            <label><input type="radio" name={`correctness_${index + 1}`} value="no"/> No</label>
                                            <label><input type="radio" name={`correctness_${index + 1}`} value="idk"/> I
                                                don't know</label>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
                <div id="results-buttons" className="button-container">
                    <button className="action-button" onClick={saveFeedback}>Save Results to CSV</button>
                    <Link to="/picture-selection">
                        <button className="action-button">Analyze More</button>
                    </Link>
                </div>
            </div>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </body>
    );
}

export default Results;