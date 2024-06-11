package protopnet.mlprototypesfeedbackcollector.model;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model class representing feedback data.
 */
@Document(collection = "feedback")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String localDate;
    private String localTime;
    private String originalImageData;
    private String prototypeImageData;
}