import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import Swal from 'sweetalert2';
import '../style.css';

function Results({birdNames, originalImgclass, predictedClass, imgdir, error}) {
    const [feedbackDataArray, setFeedbackDataArray] = useState([]);
    const [isPopupVisible, setIsPopupVisible] = useState(false);
    const [popupImageSrc, setPopupImageSrc] = useState('');
    const [unansweredIndexes, setUnansweredIndexes] = useState([]);

    const handleRadioChange = (index) => {
        setUnansweredIndexes(prevIndexes => prevIndexes.filter(i => i !== index));
    };

    const saveFeedback = async () => {
        const feedbackDataArray = [];
        let allAnswered = true;
        const newUnansweredIndexes = [];

        // Collect feedback data from radio buttons
        for (let index = 1; index <= 10; index++) {
            const selectedValue = document.querySelector(`input[name="correctness_${index}"]:checked`);
            if (!selectedValue) {
                allAnswered = false;
                newUnansweredIndexes.push(index - 1); // Store index of unanswered image
            } else {
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

        if (!allAnswered) {
            setUnansweredIndexes(newUnansweredIndexes);
            const result = await Swal.fire({
                title: 'Incomplete Feedback',
                text: 'Not all images have been evaluated. Do you want to continue?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, continue',
                cancelButtonText: 'No, go back',
                confirmButtonColor: '#45a049',
                cancelButtonColor: '#d33'
            });

            if (!result.isConfirmed) {
                return;
            }
        } else {
            setUnansweredIndexes([]); // Clear unanswered indexes if all are answered
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
        const blob = new Blob([csvContent], {type: 'text/csv'});
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.setAttribute('hidden', '');
        a.setAttribute('href', url);
        a.setAttribute('download', 'feedback_data.csv');
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    };

    const handleImageClick = (src) => {
        setPopupImageSrc(src);
        setIsPopupVisible(true);
    };

    const closePopup = () => {
        setIsPopupVisible(false);
        setPopupImageSrc('');
    };

    return (
        <div>
            <div className="header">
                <nav>
                    <ul>
                        <div className="white-box-header"><a href="/home">Home</a></div>
                        <div className="white-box-header"><a href="/about-us">About Us</a></div>
                        <div className="white-box-header"><a href="/game">Game</a></div>
                        <div className="white-box-header sign-out"><Link to="/">Sign Out</Link></div>
                    </ul>
                </nav>
            </div>
            <div className="results-container">
                <div className="left-container">
                    <div id="analysis-results" className="analysis-box">
                        <h1 className="title">Analysis Results</h1>
                        {birdNames && (
                            <div>
                                <p className="image-info">Original Image Class: <span
                                    id="originalImageClass">{birdNames[originalImgclass - 1].replace(/^\d{3}\./, '').replace(/_/g, ' ')}</span>
                                </p>
                                <p className="image-info">Predicted Class: <span
                                    id="predictedImageClass">{birdNames[predictedClass].replace(/^\d{3}\./, '').replace(/_/g, ' ')}</span>
                                </p>
                            </div>
                        )}
                    </div>
                    <div id="analysis-results-buttons" className="button-container">
                        <button className="action-button" onClick={saveFeedback}>Save Results to CSV</button>
                        <Link to="/picture-selection" className="action-button">Analyze More</Link>
                    </div>
                </div>

                <div className="center-container">
                    <div id="white-results-box" className="white-box">
                        <h2>Top 10 Most Activated Prototypes</h2>
                        <div className="scrollable-table">
                            <table className="results-table">
                                <thead>
                                <tr>
                                    <th style={{width: '35%'}}>Original Image</th>
                                    <th style={{width: '35%'}}>Prototype Image</th>
                                    <th style={{width: '30%'}}>Correctness</th>
                                </tr>
                                </thead>
                                <tbody>
                                {Array.from({length: 10}, (_, index) => (
                                    <tr key={index} className={unansweredIndexes.includes(index) ? 'unanswered' : ''}>
                                        <td>
                                            <img
                                                className="originalImagePath"
                                                src={`/results_images/${imgdir}/${index + 1}/prototype`}
                                                alt="Original Image"
                                                onClick={() => handleImageClick(`/results_images/${imgdir}/${index + 1}/prototype`)}
                                            />
                                        </td>
                                        <td>
                                            <img
                                                className="prototypeImagePath"
                                                src={`/results_images/${imgdir}/${index + 1}/activated`}
                                                alt="Prototype Image"
                                                onClick={() => handleImageClick(`/results_images/${imgdir}/${index + 1}/activated`)}
                                            />
                                        </td>
                                        <td>
                                            <div className="radio-group">
                                                <label><input type="radio" name={`correctness_${index + 1}`} value="yes"
                                                              onChange={() => handleRadioChange(index)}/> Yes</label>
                                                <label><input type="radio" name={`correctness_${index + 1}`} value="no"
                                                              onChange={() => handleRadioChange(index)}/> No</label>
                                                <label><input type="radio" name={`correctness_${index + 1}`} value="idk"
                                                              onChange={() => handleRadioChange(index)}/> I don't
                                                    know</label>
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                {error && <p style={{color: 'red'}}>{error}</p>}
            </div>
            {isPopupVisible && (
                <div className="popup-container show" onClick={closePopup}>
                    <img src={popupImageSrc} alt="Popup" className="popup-image"/>
                    <button className="popup-close" onClick={closePopup}>×</button>
                </div>
            )}
        </div>
    );
}

export default Results;