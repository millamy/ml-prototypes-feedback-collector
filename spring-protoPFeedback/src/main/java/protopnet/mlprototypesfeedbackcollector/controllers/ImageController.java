package protopnet.mlprototypesfeedbackcollector.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class ImageController {

    // Set and change in application.properties
    @Value("${local_analysis.static.path}")
    private String LOCAL_ANALYSIS_PATH;

    @Value("${modeldir.static.path}")
    private String MODELDIR_PATH;

    @Value("${model.static.path}")
    private String MODEL_PATH;

    @Value("${images_direct.static.path}")
    private String STATIC_IMAGES_PATH;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/picture-selection")
    public String showBirdSelection(Model model, HttpSession session) {
        File birdPictureFolder = new File(STATIC_IMAGES_PATH);
        Map<String, String> birdToFolder = new HashMap<>();

        if (birdPictureFolder.exists() && birdPictureFolder.isDirectory()) {
            String[] birdNames = birdPictureFolder.list();
            if (birdNames != null) {
                birdNames = Arrays.stream(birdNames).filter(name -> name.matches("\\d{3}.*")).toArray(String[]::new);

                Arrays.sort(birdNames, Comparator.comparingInt(name -> Integer.parseInt(name.substring(0, 3))));
            }

            if (birdNames != null) {
                List<String> processedBirdNames = Arrays.stream(birdNames).map(name -> {
                    String processedName = name.substring(name.indexOf('.') + 1).replace('_', ' ');
                    birdToFolder.put(processedName, name);
                    return processedName;
                }).sorted().collect(Collectors.toList());


                model.addAttribute("birdNames", processedBirdNames);
                model.addAttribute("nameToFolderMap", birdToFolder);

                session.setAttribute("birdNames", processedBirdNames);
                session.setAttribute("folderNames", birdNames);
            }
        }
        return "PictureSelection";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    private List<String> getBirdProcessedNames(String[] birdNames) {
        if (birdNames != null) {
            return Arrays.stream(birdNames).map(name -> {
                String processedName = name.substring(name.indexOf('.') + 1).replace('_', ' ');
                return processedName;
            }).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }


    // --- THAT'S THE ORIGINAL FUNCTION, LEAVING HERE JUST IN CASE !---

//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/selected-pictures")
//    @ResponseBody
//    public String analyzeSelectedPictures(@RequestParam String selectedImageUrl, @RequestParam String birdKind, @RequestParam(required = false) String[] birdNames, Model model, HttpSession session) {
//        String[] imageUrls = selectedImageUrl.split(";");
//        Integer currentImageIndex = (Integer) session.getAttribute("currentImageIndex");
//        if (currentImageIndex == null) {
//            currentImageIndex = 0;
//        }
//
//        if (currentImageIndex < imageUrls.length) {
//            String imageUrl = imageUrls[currentImageIndex];
//            String[] parts = imageUrl.split("/");
//            String imgclassStr = parts[parts.length - 2].substring(0, 3);
//            int originalImgclass = Integer.parseInt(imgclassStr);
//            int imgclass = originalImgclass - 1;
//            String imgdir = parts[parts.length - 2];
//            String img = parts[parts.length - 1];
//
//             System.out.println("imgclassStr: " + imgclassStr + "\norignalImgclass: " + originalImgclass
//                     + "\nimgclass: " + imgclass + "\nimgdir: " + imgdir + "\nimg: " + img + "\n\n");
//
//            String analysisCommand = "python " + LOCAL_ANALYSIS_PATH + " -modeldir " + MODELDIR_PATH + " -model " + MODEL_PATH + " -imgdir " + STATIC_IMAGES_PATH + imgdir + " -img " + img + " -imgclass " + imgclass;
//
//            ProcessBuilder builder;
//
//            if (System.getProperty("os.name").startsWith("Windows")) {
//                builder = new ProcessBuilder("cmd.exe", "/c", analysisCommand);
//            } else {
//                builder = new ProcessBuilder("python3", LOCAL_ANALYSIS_PATH, "-modeldir", MODELDIR_PATH, "-model", MODEL_PATH, "-imgdir", STATIC_IMAGES_PATH + imgdir, "-img", img, "-imgclass", String.valueOf(imgclass));
//            }
//
//            try {
//                builder.redirectErrorStream(true);
//                Process process = builder.start();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                StringBuilder output = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    output.append(line).append("\n");
//                }
//
//                int exitCode = process.waitFor();
//                Map<String, Object> response = new HashMap<>();
//
//                if (exitCode == 0) {
//                    String[] resultsLines = output.toString().split("\\n");
//                    String predictedClass = resultsLines[8].substring(resultsLines[8].indexOf(":") + 1).trim();
//
//                    List<Map<String, String>> prototypes = new ArrayList<>();
//
//                    String[] prototypeInfo = resultsLines[13].split(":");
//                    Map<String, String> prototypeMap = new HashMap<>();
//                    prototypeMap.put("index", prototypeInfo[1].trim());
//                    prototypeMap.put("classIdentity", prototypeInfo[1].trim());
//                    prototypes.add(prototypeMap);
//
//                    // Uncomment to get output to the terminal
//                    //System.out.println(output + "\n\n");
//
//                    model.addAttribute("birdNames", getBirdProcessedNames(birdNames));
//                    model.addAttribute("predictedClass", Integer.parseInt(prototypeInfo[1].trim()));
//                    model.addAttribute("analysisResults", output.toString());
//                    model.addAttribute("originalImgclass", originalImgclass);
//                    model.addAttribute("imgdir", imgdir);
//                    model.addAttribute("img", img);
//
//                } else {
//                    model.addAttribute("error", "Error executing the analysis command. Exit code: " + exitCode);
//                    return "Results";
//                }
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//                model.addAttribute("error", "Error executing the analysis command: " + e.getMessage());
//                return "Results";
//            }
//            session.setAttribute("currentImageIndex", currentImageIndex);
//            session.setAttribute("selectedImageUrls", selectedImageUrl);
//        } else {
//            session.removeAttribute("currentImageIndex");
//            session.removeAttribute("selectedImageUrls");
//            return "redirect:/picture-selection";
//        }
//        return "Results";
//    }

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/selected-pictures")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handlePostRequest(@RequestBody Map<String, Object> requestData, HttpSession session) {
        logger.info("handlePostRequest method started");
        String selectedImageUrl = (String) requestData.get("selectedImageUrl");
        String birdKind = (String) requestData.get("birdKind");
        List<String> birdNames = (List<String>) requestData.get("birdNames");

        if (selectedImageUrl == null || birdKind == null || birdNames == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Missing required parameters: selectedImageUrl and birdKind"));
        }

        logger.info("handlePostRequest method completed successfully");

        return analyzeImage(selectedImageUrl,birdKind,birdNames, session);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/selected-pictures")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleGetRequest(HttpSession session) {
        logger.info("Session ID in handleGetRequest: " + session.getId());
//        System.out.println(session.getAttribute("analysisResults"));

        Map<String, Object> analysisResults = (Map<String, Object>) session.getAttribute("analysisResults");

        if (analysisResults == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No analysis results available"));
        }

        return ResponseEntity.ok(analysisResults);
    }

    private ResponseEntity<Map<String, Object>> analyzeImage(String selectedImageUrl, String birdKind,List<String> birdNames, HttpSession session) {
        logger.info("Session ID in analyzeImage: " + session.getId());
        logger.info("analyzeImage method started");

        String[] imageUrls = selectedImageUrl.split(";");

        // CHANGED HERE TO 0 AS WE ARE ANALYSING ONLY ONE IMAGE FOR NOW
        // MAY NEED TO CHANGE LATER!!!!
        Integer currentImageIndex = 0;


        if (currentImageIndex >= imageUrls.length) {
            logger.info("No more images to analyze. Ending analysis.");
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Analysis completed. No more images to analyze.");
            return ResponseEntity.ok(response);
        }

        String imageUrl = imageUrls[currentImageIndex];
        String[] parts = imageUrl.split("/");
        String imgclassStr = parts[parts.length - 2].substring(0, 3);
        int originalImgclass = Integer.parseInt(imgclassStr);
        int imgclass = originalImgclass - 1;
        String imgdir = parts[parts.length - 2];
        String img = parts[parts.length - 1];

        String analysisCommand = "python " + LOCAL_ANALYSIS_PATH + " -modeldir " + MODELDIR_PATH + " -model " + MODEL_PATH + " -imgdir " + STATIC_IMAGES_PATH + imgdir + " -img " + img + " -imgclass " + imgclass;

        ProcessBuilder builder;

        if (System.getProperty("os.name").startsWith("Windows")) {
            builder = new ProcessBuilder("cmd.exe", "/c", analysisCommand);
        } else {
            builder = new ProcessBuilder("python3", LOCAL_ANALYSIS_PATH, "-modeldir", MODELDIR_PATH, "-model", MODEL_PATH, "-imgdir", STATIC_IMAGES_PATH + imgdir, "-img", img, "-imgclass", String.valueOf(imgclass));
        }

        try {
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            Map<String, Object> response = new HashMap<>();

            if (exitCode == 0) {
                String[] resultsLines = output.toString().split("\\n");
                String predictedClass = resultsLines[8].substring(resultsLines[8].indexOf(":") + 1).trim();

                response.put("birdKind", birdKind);
                response.put("predictedClass", Integer.parseInt(predictedClass));
                response.put("originalImgclass", originalImgclass);
                response.put("imgdir", imgdir);
                response.put("img", img);
                response.put("birdNames", birdNames);

                session.setAttribute("analysisResults", null);
                session.setAttribute("analysisResults", response);
                System.out.println("Analysis results: " + response);

                session.setAttribute("currentImageIndex", currentImageIndex + 1);
                logger.info("analyzeImage method completed successfully");

                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Error executing the analysis command. Exit code: " + exitCode);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Error executing the analysis command: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



// --- only selecting one picture for now ----

//    @CrossOrigin(origins = "http://localhost:3000")
//    @GetMapping("/analyze-next")
//    public String analyzeNextImage(HttpSession session, Model model) {
//        Integer currentImageIndex = (Integer) session.getAttribute("currentImageIndex");
//        String selectedImageUrls = (String) session.getAttribute("selectedImageUrls");
//
//        if (currentImageIndex != null && selectedImageUrls != null) {
//            session.setAttribute("currentImageIndex", currentImageIndex + 1);
//            return analyzeSelectedPictures(selectedImageUrls, null, model, session);
//        } else {
//            return "redirect:/picture-selection";
//        }
//    }

    //Fetching images
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/images/{folderName}")
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getBirdImages(@PathVariable String folderName) {
        List<Map<String, String>> imageDatas = new ArrayList<>();
        File birdFolder = new File(STATIC_IMAGES_PATH + folderName);

        if (birdFolder.exists() && birdFolder.isDirectory()) {
            File[] birdImages = birdFolder.listFiles();
            if (birdImages != null) {
                for (File file : birdImages) {
                    if (file.isFile()) {
                        try {
                            byte[] imageData = Files.readAllBytes(file.toPath());
                            String base64Image = Base64.getEncoder().encodeToString(imageData);


                            Map<String, String> imageDataMap = new HashMap<>();
                            imageDataMap.put("imageData", base64Image);
                            imageDataMap.put("folderPath", folderName + "/" + file.getName());

                            imageDatas.add(imageDataMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return ResponseEntity.ok(imageDatas);
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/results_images/{imgdir}/{index}/{type}")
    public ResponseEntity<byte[]> getGeneratedImage(@PathVariable String imgdir, @PathVariable String index, @PathVariable String type) {

        String filePath;
        if (type.equals("prototype")) {
            filePath = STATIC_IMAGES_PATH + imgdir + "/vgg19/001/100_0push0.7411.pth/most_activated_prototypes/prototype_activation_map_by_top-" + index + "_prototype.png";
        } else {
            filePath = STATIC_IMAGES_PATH + imgdir + "/vgg19/001/100_0push0.7411.pth/most_activated_prototypes/top-" + index + "_activated_prototype_self_act.png";
        }

        try {
            Path path = Paths.get(filePath);
            byte[] imageData = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/bird-names")
    public ResponseEntity<List<String>> getBirdNames() {
        File birdPictureFolder = new File(STATIC_IMAGES_PATH);
        if (birdPictureFolder.exists() && birdPictureFolder.isDirectory()) {
            String[] birdNames = birdPictureFolder.list();
            if (birdNames != null) {
                birdNames = Arrays.stream(birdNames).filter(name -> name.matches("\\d{3}.*")).sorted(Comparator.comparingInt(name -> Integer.parseInt(name.substring(0, 3)))).toArray(String[]::new);

                List<String> fullBirdNames = Arrays.asList(birdNames);

                // Return bird names as JSON
                return ResponseEntity.ok(fullBirdNames);
            }
        }
        return ResponseEntity.notFound().build();
    }


   }