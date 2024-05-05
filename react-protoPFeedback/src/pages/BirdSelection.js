import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import '../style.css';


function PictureSelection() {
    const [birdNames, setBirdNames] = useState([]);
    const [selectedBird, setSelectedBird] = useState('');
    const [birdImages, setBirdImages] = useState([]);
    const [selectedImages, setSelectedImages] = useState([]);
    const maxSelectedImages = 1;

    // Fetch bird names from the Spring Boot backend
    useEffect(() => {
        fetch('/api/bird-names')
            .then(response => response.json())
            .then(data => {
                setBirdNames(data);
                if (data.length > 0) {
                    setSelectedBird(data[0]);
                }
            })
            .catch(error => console.error('Error fetching bird names:', error));
    }, []);

    useEffect(() => {
        if (selectedBird) {
            fetch(`/images/${selectedBird}`)
                .then(response => response.json())
                .then(data => setBirdImages(data))
                .catch(error => console.error('Error fetching images:', error));
        }
    }, [selectedBird]);

    function handleBirdChange(event) {
        setSelectedBird(event.target.value);
    }


    function handleImageClick(imagePath) {
        if (selectedImages.includes(imagePath)) {
            setSelectedImages(prevImages => prevImages.filter(image => image !== imagePath));
        } else {
            if (selectedImages.length < maxSelectedImages) {
                setSelectedImages(prevImages => [...prevImages, imagePath]);
            } else {
                alert('You can only select only one picture');
            }
        }
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        const selectedImageUrl = selectedImages.length > 0 ? selectedImages[0] : '';
        const formData = new FormData();
        formData.append('selectedImageUrl', selectedImageUrl);
        formData.append('birdKind', selectedBird);
        formData.append('birdNames', JSON.stringify(birdNames));

        fetch('http://localhost:8080/selected-pictures', {
            method: 'POST',
            body: formData,
        })
            .then(response => {
                if (response.ok) {
                    // Handle successful submission (e.g., show a success message or redirect)
                    console.log('Submission successful');
                } else {
                    // Handle error
                    console.error('Submission failed');
                }
            })
            .catch(error => {
                console.error('Error during submission:', error);
            });
    };

    function formatBirdName(name) {
        return name.replace(/(\d+\.)|_/g, ' ').trim();
    }

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

            <div className="content-container">
                <div className="white-box">
                    <h2>Please select a bird kind</h2>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="birdKind">Available bird kinds:</label>
                        <select
                            id="birdKind"
                            name="birdKind"
                            value={selectedBird}
                            onChange={handleBirdChange}
                        >
                            {birdNames.map(birdName => (
                                <option key={birdName} value={birdName}>
                                    {formatBirdName(birdName)}
                                </option>
                            ))}
                        </select>
                        <br />
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
                        <input type="hidden" id="selectedImageUrl" name="selectedImageUrl" value={selectedImages.join(';')} />
                        <button type="submit">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default PictureSelection;