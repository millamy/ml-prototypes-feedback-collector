package protopnet.mlprototypesfeedbackcollector.util;

import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageUtil {
    public static Binary convertImageToBinary(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        byte[] imageData = Files.readAllBytes(path);
        return new Binary(imageData);
    }

    public static Binary convertBase64ToBinary(String base64Data) {
        if (base64Data.contains(",")) {
            base64Data = base64Data.split(",")[1];
        }
        byte[] imageData = Base64.getDecoder().decode(base64Data);
        return new Binary(imageData);
    }

    public static String convertBinaryToImageDataUri(Binary binaryData) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(binaryData.getData());
    }
}
