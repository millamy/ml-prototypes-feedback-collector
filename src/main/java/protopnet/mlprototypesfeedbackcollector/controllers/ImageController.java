package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Controller
public class ImageController {
    private static final String STATIC_IMAGES_PATH = "src/main/resources/static/bird_pictures/";

    @GetMapping("/picture-selection")
    public String showBirdSelection(Model model) {
        File birdPictureFolder = new File(STATIC_IMAGES_PATH);


        if (birdPictureFolder.exists() && birdPictureFolder.isDirectory()) {
            String[] birdNames = birdPictureFolder.list();
            model.addAttribute("birdNames", Arrays.asList(Objects.requireNonNull(birdNames)));
        }
        return "PictureSelection";
    }
}
