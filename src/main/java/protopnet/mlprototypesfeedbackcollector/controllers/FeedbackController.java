package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FeedbackController {

    @Value("${feedback_csv.static.path}")
    private String CSV_FILE_PATH;

    @PutMapping("/save-feedback")
    public ResponseEntity<Map<String, Object>> saveFeedback(@RequestBody List<FeedbackData> feedbackDataList) {
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {

            File file = new File(CSV_FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            // Append new data to CSV
            for (FeedbackData feedbackData : feedbackDataList) {
                writer.append(String.join(",", currentUserName(), feedbackData.toCsv()));
                writer.append("\n");
            }
            writer.flush();

            // Return success
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            // Return error response
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error occurred while saving feedback data.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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