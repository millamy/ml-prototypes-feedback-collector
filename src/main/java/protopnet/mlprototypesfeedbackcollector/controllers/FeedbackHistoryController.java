package protopnet.mlprototypesfeedbackcollector.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.service.FeedbackService;
import protopnet.mlprototypesfeedbackcollector.util.ImageUtil;

import java.util.List;

/**
 * Controller class for handling feedback history-related requests.
 */
@Controller
public class FeedbackHistoryController {

    private final FeedbackService feedbackService;

    /**
     * Constructor for FeedbackHistoryController.
     *
     * @param feedbackService the feedback service to be used by the controller.
     */
    @Autowired
    public FeedbackHistoryController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Endpoint for retrieving all feedbacks.
     *
     * @param model the model to be used for adding attributes.
     * @return the name of the Thymeleaf/JSP template to display feedbacks.
     */
    @GetMapping("/history")
    public String getAllFeedbacks(Model model) {
        List<FeedbackData> feedbackList = feedbackService.getAllFeedbacks();

        feedbackList.forEach(feedback -> {
            String originalImageDataUri = ImageUtil.convertBinaryToImageDataUri(feedback.getOriginalImage());
            String prototypeImageDataUri = ImageUtil.convertBinaryToImageDataUri(feedback.getPrototypeImage());

            feedback.setOriginalImagePath(originalImageDataUri);
            feedback.setPrototypeImagePath(prototypeImageDataUri);
        });

        model.addAttribute("feedbacks", feedbackList);
        return "History";
    }
}
