import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import '../style.css';

function BirdSelection() {
    const [birdNames, setBirdNames] = useState([]);
    const [birdKind, setBirdKind] = useState('');
    const [birdImages, setBirdImages] = useState([]);
    const [selectedImages, setSelectedImages] = useState([]);
    const maxSelectedImages = 1;
    const navigate = useNavigate();

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
            alert('Please select an image before submitting.');
            return;
        }

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

                navigate('/results');
            })
            .catch(error => console.error('Error:', error));
    };

    const cleanedBirdNames = birdNames.map(name => name.replace(/^\d{3}\./, '').replace(/_/g, ' '));

    return (
        <div className="content-container">
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
                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    );
}

export default BirdSelection;