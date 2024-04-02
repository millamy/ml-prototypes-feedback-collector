package protopnet.mlprototypesfeedbackcollector.model;

import jakarta.websocket.Decoder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.Binary;

@Document(collection = "feedback")
@Data
public class FeedbackData {
    @Id
    private String id;
    private String username;
    private String imageClass;
    private String predictedImageClass;
    private Binary originalImage;
    private Binary prototypeImage;
    private String originalImagePath;
    private String prototypeImagePath;
    private String correctness;
    private Integer selectionX;
    private Integer selectionY;
    private Integer selectionWidth;
    private Integer selectionHeight;

    public FeedbackData(String id, String imageClass, String predictedImageClass,String originalImagePath,  String prototypeImagePath, String correctness) {
        this.id = id;
        this.imageClass = imageClass;
        this.predictedImageClass = predictedImageClass;
        this.originalImagePath = originalImagePath;
        this.prototypeImagePath = prototypeImagePath;
        this.correctness = correctness;
    }

}