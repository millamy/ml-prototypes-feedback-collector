package protopnet.mlprototypesfeedbackcollector.model;
import jakarta.websocket.Decoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.Binary;

import java.util.Date;

@Document(collection = "feedback")
@Getter
@Setter
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

    private Date date;

    public FeedbackData(String id, String imageClass, String predictedImageClass,String originalImagePath,  String prototypeImagePath, String correctness, Date date) {
        this.id = id;
        this.imageClass = imageClass;
        this.predictedImageClass = predictedImageClass;
        this.originalImagePath = originalImagePath;
        this.prototypeImagePath = prototypeImagePath;
        this.correctness = correctness;
        this.date = date;
    }

}