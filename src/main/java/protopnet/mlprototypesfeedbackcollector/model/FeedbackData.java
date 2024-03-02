package protopnet.mlprototypesfeedbackcollector.model;
import jakarta.websocket.Decoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.Binary;

@Document(collection = "feedback")
public class FeedbackData {
    @Id
    private String id;
    @Setter
    private String username;
    private String imageClass;
    private String predictedImageClass;
    @Getter
    @Setter
    private Binary originalImage;
    @Setter
    private Binary prototypeImage;
    @Getter
    private String originalImagePath;
    @Getter
    private String prototypeImagePath;
    private String correctness;

    public FeedbackData(String id, String imageClass, String predictedImageClass,String originalImagePath,  String prototypeImagePath, String correctness) {
        this.id = id;
        this.imageClass = imageClass;
        this.predictedImageClass = predictedImageClass;
        this.originalImagePath = originalImagePath;
        this.prototypeImagePath = prototypeImagePath;
        this.correctness = correctness;
    }

}