package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ImageController {
    private static final String STATIC_IMAGES_PATH = "src/main/resources/static/bird_pictures/";

    @GetMapping("/picture-selection")
    public String showBirdSelection(Model model) {
        File birdPictureFolder = new File(STATIC_IMAGES_PATH);
        Map<String, String> birdToFolder = new HashMap<>();

        if (birdPictureFolder.exists() && birdPictureFolder.isDirectory()) {
            String[] birdNames = birdPictureFolder.list();

            if (birdNames != null) {
                List<String> processedBirdNames = Arrays.stream(birdNames)
                        .map(name -> {
                            String processedName = name.substring(name.indexOf('.') + 1).replace('_', ' ');
                            birdToFolder.put(processedName, name);
                            return processedName;
                        })
                        .sorted()
                        .collect(Collectors.toList());

                model.addAttribute("birdNames", processedBirdNames);
                model.addAttribute("nameToFolderMap", birdToFolder);
            }
        }
        return "PictureSelection";
    }

    @GetMapping("/images/{birdName}")
    @ResponseBody
    public ResponseEntity<List<String>> getBirdImages(@PathVariable String birdName) {
        File birdFolder = new File(STATIC_IMAGES_PATH + birdName);
        List<String> imageUrls = new ArrayList<>();

        if (birdFolder.exists() && birdFolder.isDirectory()) {
            File[] birdImages = birdFolder.listFiles();
           if (birdImages != null) {
               for (File file : birdImages) {
                   if (file.isFile()) {
                       imageUrls.add("bird_pictures/" + birdName + "/" + file.getName());
                   }
               }
           }
        }
        return ResponseEntity.ok(imageUrls);
    }
}
