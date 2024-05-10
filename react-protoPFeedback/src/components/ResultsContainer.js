import React, { useState, useEffect } from 'react';
import Results from '../pages/Results';

function ResultsContainer() {
    const [birdNames, setBirdNames] = useState(null);
    const [originalImgclass, setOriginalImgclass] = useState(null);
    const [predictedClass, setPredictedClass] = useState(null);
    const [imgdir, setImgdir] = useState(null);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {

        fetch('http://localhost:8080/selected-pictures', {
            credentials: 'include',
        })
            .then(response => response.json())
            .then(data => {
                setBirdNames(data.birdNames);
                setOriginalImgclass(data.originalImgclass);
                setPredictedClass(data.predictedClass);
                setImgdir(data.imgdir);
                setIsLoading(false);
            })
            .catch(error => {
                setError(error.message);
                setIsLoading(false);
            });

    }, []);



    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            {}
                <Results
                    originalImgclass={originalImgclass}
                    predictedClass={predictedClass}
                    imgdir={imgdir}
                    birdNames={birdNames}
                />
            {}
        </div>
    );
}

export default ResultsContainer;