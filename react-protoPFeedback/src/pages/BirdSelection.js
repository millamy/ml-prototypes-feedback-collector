import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import '../style.css';

function BirdSelection() {
    const [birdNames, setBirdNames] = useState([]);
    const [birdKind, setBirdKind] = useState('');
    const [birdImages, setBirdImages] = useState([]);
    const [selectedImages, setSelectedImages] = useState([]);
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        fetch('/api/bird-names')
            .then(response => response.json())
            .then(data => {
                setBirdNames(data);
                if (data.length > 0) {
                    setBirdKind(data[0]);
                }
            })
            .catch(error => console.error('Error fetching bird names:', error));
    }, []);

    useEffect(() => {
        if (birdKind) {
            fetch(`/images/${birdKind}`)
                .then(response => response.json())
                .then(data => setBirdImages(data))
                .catch(error => console.error('Error fetching images:', error));
        }
    }, [birdKind]);

    function handleBirdChange(event) {
        const newBirdKind = event.target.value;
        if (newBirdKind !== birdKind) {
            setSelectedImages([]);
        }
        setBirdKind(newBirdKind);
    }

    function handleImageClick(imagePath) {
        setSelectedImages([imagePath]);
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        if (selectedImages.length === 0) {
            Swal.fire({
                icon: 'warning',
                iconColor: '#ea2828',
                title: 'No image selected',
                text: 'Please select an image before submitting.',
                confirmButtonText: 'OK',
                customClass: {
                    confirmButton: 'my-confirm-button',
                    title: 'my-title',
                    popup: 'my-popup'
                },
                confirmButtonColor: "#4CAF50"
            });
            return;
        }

        setIsLoading(true);

        const selectedImageUrl = selectedImages.length > 0 ? selectedImages[0] : '';
        fetch('http://localhost:8080/selected-pictures', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                selectedImageUrl: selectedImageUrl,
                birdKind: birdKind,
                birdNames: birdNames
            }),
            credentials: 'include',
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setIsLoading(false);
                navigate('/results');
            })
            .catch(error =>  {
                console.error('Error:', error)
                setIsLoading(false);
            });
    };

    const cleanedBirdNames = birdNames.map(name => name.replace(/^\d{3}\./, '').replace(/_/g, ' '));

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
            <div className={`content-container ${isLoading ? 'loading' : ''}`}>
                <div className="white-box">
                    <h2>Please select a bird kind</h2>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="birdKind">Available bird kinds:</label>
                        <select
                            id="birdKind"
                            name="birdKind"
                            value={birdKind}
                            onChange={handleBirdChange}
                        >
                            {birdNames.map((birdName, index) => (
                                <option key={birdName} value={birdName}>
                                    {cleanedBirdNames[index]}
                                </option>
                            ))}
                        </select>
                        <br/>
                        <div id="birdsImagesGrid" className="image-grid">
                            {birdImages.map(record => (
                                <img
                                    key={record.folderPath}
                                    src={`data:image/jpeg;base64,${record.imageData}`}
                                    alt="Bird Image"
                                    className={`image-item ${selectedImages.includes(record.folderPath) ? 'selected' : ''}`}
                                    onClick={() => handleImageClick(record.folderPath)}
                                />
                            ))}
                        </div>
                        <div className="button-container">
                            <button id="submit-button" type="submit">Submit</button>
                        </div>
                    </form>
                    {isLoading && (
                        <div className="overlay">
                            <div className="loading-spinner"></div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default BirdSelection;