import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import '../style.css';

function PictureSelection() {
    // const navigate = useNavigate();
    //
    // const [birdNames, setBirdNames] = useState([]);
    // const [birdImages, setBirdImages] = useState([]);
    // const maxSelectedImages = 3;

    // // Fetch bird names from the Spring Boot backend
    // useEffect(() => {
    //     fetch('/api/bird-names')
    //         .then(response => response.json())
    //         .then(data => {
    //             setBirdNames(data);
    //             if (data.length > 0) {
    //                 setSelectedBird(data[0]);
    //             }
    //         })
    //         .catch(error => console.error('Error fetching bird names:', error));
    // }, [setSelectedBird]);

    // // Fetch images for the selected bird
    // useEffect(() => {
    //     if (selectedBird) {
    //         fetch(`/images/${selectedBird}`)
    //             .then(response => response.json())
    //             .then(data => setBirdImages(data))
    //             .catch(error => console.error('Error fetching images:', error));
    //     }
    // }, [selectedBird]);
    //
    // function handleBirdChange(event) {
    //     setSelectedBird(event.target.value);
    // }
    //
    // function handleImageClick(imagePath) {
    //     if (selectedImages.includes(imagePath)) {
    //         setSelectedImages(prevImages => prevImages.filter(image => image !== imagePath));
    //     } else if (selectedImages.length < maxSelectedImages) {
    //         setSelectedImages(prevImages => [...prevImages, imagePath]);
    //     } else {
    //         alert('You can only select up to 3 bird pictures.');
    //     }
    // }
    //
    // const handleSubmit = (event) => {
    //     event.preventDefault();
    //
    //     // const formData = new FormData();
    //     // formData.append('selectedImages', selectedImages.join(';'));
    //     // formData.append('birdKind', selectedBird);
    //
    //     // Send the data to the server
    //     fetch('http://localhost:8080/selected-pictures', {
    //         method: 'POST',
    //         body: formData,
    //     })
    //         .then(response => {
    //             if (response.ok) {
    //                 console.log('Submission successful');
    //                 navigate('/selected-pictures');
    //             } else {
    //                 console.error('Submission failed');
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error during submission:', error);
    //         });
    // };

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

            {/*<div className="content-container">*/}
            {/*    <div className="white-box">*/}
            {/*        <h2>Please select a bird kind</h2>*/}
            {/*        <form onSubmit={handleSubmit}>*/}
            {/*            <label htmlFor="birdKind">Available bird kinds:</label>*/}
            {/*            <select*/}
            {/*                id="birdKind"*/}
            {/*                name="birdKind"*/}
            {/*                value={selectedBird}*/}
            {/*                onChange={handleBirdChange}*/}
            {/*            >*/}
            {/*                {birdNames.map(birdName => (*/}
            {/*                    <option key={birdName} value={birdName}>*/}
            {/*                        {birdName}*/}
            {/*                    </option>*/}
            {/*                ))}*/}
            {/*            </select>*/}

            {/*            <br />*/}

            {/*            <div id="birdsImagesGrid" className="image-grid">*/}
            {/*                {birdImages.map(record => (*/}
            {/*                    <img*/}
            {/*                        key={record.folderPath}*/}
            {/*                        src={`data:image/jpeg;base64,${record.imageData}`}*/}
            {/*                        alt="Bird Image"*/}
            {/*                        className={`image-item ${selectedImages.includes(record.folderPath) ? 'selected' : ''}`}*/}
            {/*                        onClick={() => handleImageClick(record.folderPath)}*/}
            {/*                    />*/}
            {/*                ))}*/}
            {/*            </div>*/}

            {/*            <input type="hidden" id="selectedImageUrl" name="selectedImageUrl" value={selectedImages.join(';')} />*/}
            {/*            <button type="submit">Submit</button>*/}
            {/*        </form>*/}
            {/*    </div>*/}
            {/*</div>*/}
        </div>
    );
}

export default PictureSelection;
