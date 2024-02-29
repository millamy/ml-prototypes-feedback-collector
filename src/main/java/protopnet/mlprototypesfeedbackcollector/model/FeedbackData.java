package protopnet.mlprototypesfeedbackcollector.model;

public class FeedbackData {
    private String imageClass;
    private String predictedImageClass;
    private String originalImagePath;
    private String prototypeImagePath;
    private String correctness;

    public FeedbackData(String imageClass, String predictedImageClass, String originalImagePath, String prototypeImagePath, String correctness) {
        this.imageClass = imageClass;
        this.predictedImageClass = predictedImageClass;
        this.originalImagePath = originalImagePath;
        this.prototypeImagePath = prototypeImagePath;
        this.correctness = correctness;
    }

    public String toCsv() {
        return String.join(",", imageClass, predictedImageClass, originalImagePath, prototypeImagePath, correctness);
    }
}