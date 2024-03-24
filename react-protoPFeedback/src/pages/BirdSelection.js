import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../style.css';

const BirdSelection = () => {
    const [birdNames, setBirdNames] = useState([]);
    const [nameToFolderMap, setNameToFolderMap] = useState({});

    const [selectedBirdKind, setSelectedBirdKind] = useState('');
    const [birdImages, setBirdImages] = useState([]);
    const [selectedImages, setSelectedImages] = useState([]);

    const maxSelectedImages = 3;

    useEffect(() => {
        const fetchedBirdNames = [];
        const fetchedNameToFolderMap = {};
        setBirdNames(fetchedBirdNames);
        setNameToFolderMap(fetchedNameToFolderMap);
        if (fetchedBirdNames.length > 0) {
            setSelectedBirdKind(fetchedBirdNames[0]);
        }
    }, []);

    useEffect(() => {
        loadBirdImages(selectedBirdKind);
    }, [selectedBirdKind, nameToFolderMap]);

    const loadBirdImages = (birdName) => {
        const folderName = nameToFolderMap[birdName];
        fetch(`/images/${folderName}`)
            .then(response => response.json())
            .then(data => {
                setBirdImages(data.map(record => ({
                    ...record,
                    blobUrl: base64ToBlob(record.imageData)
                })));
            })
            .catch(error => console.error('Error fetching images:', error));
    };

    const handleImageSelect = (imagePath) => {
        const isSelected = selectedImages.includes(imagePath);
        if (isSelected) {
            setSelectedImages(selectedImages.filter(image => image !== imagePath));
        } else if (selectedImages.length < maxSelectedImages) {
            setSelectedImages([...selectedImages, imagePath]);
        } else {
            alert('You can only select up to 3 bird pictures.');
        }
    };

    const base64ToBlob = (base64Data) => {
        const byteCharacters = atob(base64Data);
        const byteArrays = [];
        for (let offset = 0; offset < byteCharacters.length; offset += 512) {
            const slice = byteCharacters.slice(offset, offset + 512);
            const byteNumbers = new Array(slice.length);
            for (let i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }
            byteArrays.push(new Uint8Array(byteNumbers));
        }
        return URL.createObjectURL(new Blob(byteArrays, { type: 'image/jpeg' }));
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        // TODO: Submit selected images to the server
    };

    return (
        <div>
            <h2>Please select a bird kind</h2>
            <form onSubmit={handleSubmit}>
                <label htmlFor="birdKind">Available bird kinds:</label>
                <select
                    id="birdKind"
                    name="birdKind"
                    value={selectedBirdKind}
                    onChange={(e) => setSelectedBirdKind(e.target.value)}
                >
                    {birdNames.map((birdName) => (
                        <option key={birdName} value={birdName}>
                            {birdName}
                        </option>
                    ))}
                </select>
                <div className="image-grid">
                    {birdImages.map((image, index) => (
                        <img
                            key={index}
                            src={image.blobUrl}
                            alt="Bird"
                            className={`image-item ${selectedImages.includes(image.folderPath) ? 'selected' : ''}`}
                            onClick={() => handleImageSelect(image.folderPath)}
                        />
                    ))}
                </div>
                <input type="hidden" id="selectedImageUrl" name="selectedImageUrl" value={selectedImages.join(';')} />
                <button type="submit">Submit</button>
            </form>
        </div>
    );
};

export default BirdSelection;
