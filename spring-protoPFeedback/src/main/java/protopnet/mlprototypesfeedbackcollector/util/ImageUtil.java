package protopnet.mlprototypesfeedbackcollector.util;

import org.bson.types.Binary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUtil {
    public static Binary convertImageToBinary(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        byte[] imageData = Files.readAllBytes(path);
        return new Binary(imageData);
    }

    // not in use right now but may be usefull in future & for testing
    public static void convertBinaryToImage(Binary binary, String outputPath) throws IOException {
        byte[] imageData = binary.getData();
        Files.write(Paths.get(outputPath), imageData);
    }
}
