package protopnet.mlprototypesfeedbackcollector.util;

import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Utility class for handling image conversions.
 */
public class ImageUtil {

    /**
     * Converts an image file to a Binary object.
     *
     * @param imagePath the path to the image file.
     * @return a Binary object containing the image data.
     * @throws IOException if an I/O error occurs reading the file.
     */
    public static Binary convertImageToBinary(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        byte[] imageData = Files.readAllBytes(path);
        return new Binary(imageData);
    }

    /**
     * Converts a Base64 encoded string to a Binary object.
     *
     * @param base64Data the Base64 encoded string.
     * @return a Binary object containing the image data.
     */
    public static Binary convertBase64ToBinary(String base64Data) {
        if (base64Data.contains(",")) {
            base64Data = base64Data.split(",")[1];
        }
        byte[] imageData = Base64.getDecoder().decode(base64Data);
        return new Binary(imageData);
    }

    /**
     * Converts a Binary object to a Base64 encoded data URI string.
     *
     * @param binaryData the Binary object containing the image data.
     * @return a Base64 encoded data URI string representing the image.
     */
    public static String convertBinaryToImageDataUri(Binary binaryData) {
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(binaryData.getData());
    }
}
