package protopnet.mlprototypesfeedbackcollector.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class ImageController {
    private static final String STATIC_IMAGES_PATH = "src/main/resources/static/bird_pictures/";

    @GetMapping("/picture-selection")
    public String showBirdSelection(Model model, HttpSession session) {
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

                session.setAttribute("birdNames", processedBirdNames);
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

    @PostMapping("/selected-pictures")
    public String analyzeSelectedPictures(@RequestParam String selectedImageUrl, @RequestParam String birdKind, Model model, HttpSession session) {
        String[] imageUrls = selectedImageUrl.split(";");

        // TO DO: Analyze each image separately
        for (String imageUrl : imageUrls) {
            String[] parts = selectedImageUrl.split("/");
            String imgclassStr = parts[parts.length - 2].substring(0, 3);
            int originalImgclass = Integer.parseInt(imgclassStr);
            int imgclass = originalImgclass - 1;
            String imgdir = parts[parts.length - 2];
            String img = parts[parts.length - 1];

            //IMPORTANT: Change the paths accordingly !!!
            String analysisCommand = "python C:\\Users\\User\\Downloads\\ProtoPNet\\local_analysis.py  -modeldir C:\\Users\\User\\Downloads\\ProtoPNet\\saved_models\\vgg19\\001 " +
                    "-model ./100_0push0.7411.pth -imgdir C:\\Users\\User\\IdeaProjects\\ml-prototypes-feedback-collector\\resources\\static\\bird_pictures\\"
                    + imgdir + " -img " + img + " -imgclass " + imgclass;

            try {
                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", analysisCommand);
                builder.redirectErrorStream(true);
                Process process = builder.start();

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    String[] resultsLines = output.toString().split("\\n");
                    String predictedClass = resultsLines[8].substring(resultsLines[8].indexOf(":") + 1).trim();

                    List<Map<String, String>> prototypes = new ArrayList<>();

                    String[] prototypeInfo = resultsLines[13].split(":");
                    Map<String, String> prototypeMap = new HashMap<>();
                    prototypeMap.put("index", prototypeInfo[1].trim());
                    prototypeMap.put("classIdentity", prototypeInfo[1].trim());
                    prototypes.add(prototypeMap);

//                Uncomment to get output to the terminal
//                System.out.println(output);
                    List<String> birdNames = (List<String>) session.getAttribute("birdNames");

                    model.addAttribute("birdNames", birdNames);
                    model.addAttribute("predictedClass", Integer.parseInt(prototypeInfo[1].trim()));
                    model.addAttribute("analysisResults", output.toString());
                    model.addAttribute("originalImgclass", originalImgclass);
                    model.addAttribute("imgdir", imgdir);
                    model.addAttribute("img", img);

                } else {
                    model.addAttribute("error", "Error executing the analysis command. Exit code: " + exitCode);
                    return "Results";
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                model.addAttribute("error", "Error executing the analysis command: " + e.getMessage());
                return "Results";
            }
        }
        return "Results";
    }
}
