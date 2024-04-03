package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.service.FeedbackService;

import java.util.Base64;
import java.util.List;

@Controller
public class FeedbackHistoryController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackHistoryController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping("/history")
    public String getAllFeedbacks(Model model) {
        List<FeedbackData> feedbackList = feedbackService.getAllFeedbacks();

        feedbackList.forEach(feedback -> {
            byte[] originalImageBytes = feedback.getOriginalImage().getData(); // Pobierz dane obrazu binarnego
            String originalImageDataUri = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(originalImageBytes); // Przetwórz obraz na dane URI

            byte[] prototypeImageBytes = feedback.getPrototypeImage().getData(); // Pobierz dane obrazu binarnego
            String prototypeImageDataUri = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(prototypeImageBytes); // Przetwórz obraz na dane URI

            feedback.setOriginalImagePath(originalImageDataUri); // Ustaw przetworzony obraz jako ścieżkę obrazu
            feedback.setPrototypeImagePath(prototypeImageDataUri); // Ustaw przetworzony obraz jako ścieżkę obrazu
        });

        model.addAttribute("feedbacks", feedbackList );
        return "History"; // Name of the Thymeleaf/JSP template to display feedbacks
    }
}
