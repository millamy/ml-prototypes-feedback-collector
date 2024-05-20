package protopnet.mlprototypesfeedbackcollector.controllers;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.service.FeedbackService;
import protopnet.mlprototypesfeedbackcollector.util.ImageUtil;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FeedbackController {

    @Value("${images_direct.static.path}")
    private String STATIC_IMAGES_PATH;
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PutMapping("/save-feedback")
    public ResponseEntity<Map<String, Object>> saveFeedback(@RequestBody List<FeedbackData> feedbackList) {
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            for (FeedbackData feedback : feedbackList) {
                feedback.setUsername(currentUserName());

                // Convert base64 data to Binary
                feedback.setOriginalImage(new Binary(decodeBase64Image(feedback.getOriginalImageData())));
                feedback.setPrototypeImage(new Binary(decodeBase64Image(feedback.getPrototypeImageData())));

                feedback.setLocalDate(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                feedback.setLocalTime(dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                feedbackService.saveFeedback(feedback);
            }

            // Return success
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            // Return error response
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error occurred while saving feedback data.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private byte[] decodeBase64Image(String base64ImageData) {
        // Remove the "data:image/png;base64," prefix if it exists
        if (base64ImageData.contains(",")) {
            base64ImageData = base64ImageData.split(",")[1];
        }
        return Base64.getDecoder().decode(base64ImageData);
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}

