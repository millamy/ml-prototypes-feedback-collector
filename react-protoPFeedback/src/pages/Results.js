import React, { useEffect, useState } from 'react';
import '../style.css';

function Results({ birdNames, originalImgclass, predictedClass, imgdir, error }) {
    const [feedbackDataArray, setFeedbackDataArray] = useState([]);
    console.log('imgdir:', imgdir);
    console.log('birdNames:', birdNames);
    console.log('originalImgclass:', originalImgclass);

    const saveFeedback = () => {
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
                setFeedbackDataArray(prevData => [...prevData, feedbackData]);
            }
        }
        console.log(feedbackDataArray);

        const feedbackDataJson = JSON.stringify(feedbackDataArray);
        fetch('http://localhost:8080/save-feedback', {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json'
            },
            body: feedbackDataJson
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
            })
            .catch(error => {
                console.error('Error parsing JSON from response:', error);
            });
    };

    const saveFeedbackAndRedirect = async () => {
        await saveFeedback();
        window.location.href = "/analyze-next";
    };

    return (
        <div>

            <div className="header">
                <nav>
                    <ul>
                        <div className="white-box-header"><a href="/home">Home</a></div>
                        <div className="white-box-header"><a href="/about-us">About Us</a></div>
                    </ul>
                </nav>
            </div>
            <div className="results-container">
                <div className="analysis-box">
                    <h1 className="title">Analysis Results</h1>
                    {birdNames && (
                        <div>
                            <p className="image-info">Original Image Class: <span id="originalImageClass">{birdNames[originalImgclass - 1]}</span></p>
                            <p className="image-info">Predicted Class: <span id="predictedImageClass">{birdNames[predictedClass]}</span></p>
                        </div>
                    )}
                </div>
            </div>

            <div className="center-container">
                <div className="white-box">
                    <h2>Top 10 Most Activated Prototypes</h2>
                    <div className="scrollable-table">
                        <table border="1">
                            <thead>
                            <tr>
                                <th style={{width: '35%'}}>Original Image</th>
                                <th style={{width: '35%'}}>Prototype Image</th>
                                <th style={{width: '30%'}}>Correctness</th>
                            </tr>
                            </thead>
                            <tbody>
                            {/* Replace this with your actual data */}
                            {Array.from({ length: 10 }, (_, index) => (
                                <tr key={index}>
                                    <td>
                                        <img className="originalImagePath" src={`/results_images/${imgdir}/${index + 1}/prototype`} alt="Original Image"/>
                                    </td>
                                    <td>
                                        <img className="prototypeImagePath" src={`/results_images/${imgdir}/${index + 1}/activated`} alt="Prototype Image"/>
                                    </td>
                                    <td>
                                        <label><input type="radio" name={`correctness_${index + 1}`} value="yes" /> Yes</label>
                                        <label><input type="radio" name={`correctness_${index + 1}`} value="no"/> No</label>
                                        <label><input type="radio" name={`correctness_${index + 1}`} value="idk" /> I don't know</label>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
                <div className="next-button">
                    <button className="action-button" onClick={saveFeedbackAndRedirect}>Save & Continue</button>
                </div>
            </div>

            {/* Display any errors */}
            {error && <p style={{color: 'red'}}>{error}</p>}
        </div>
    );
}

export default Results;