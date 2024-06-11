package protopnet.mlprototypesfeedbackcollector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.repository.FeedbackRepository;

import java.util.List;

/**
 * Service class for handling feedback-related operations.
 */
@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    /**
     * Constructor for FeedbackService.
     *
     * @param feedbackRepository the feedback repository to be used by the service.
     */
    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Saves the given feedback data.
     *
     * @param feedback the feedback data to save.
     * @return the saved feedback data.
     */
    public FeedbackData saveFeedback(FeedbackData feedback) {
        return feedbackRepository.save(feedback);
    }

    /**
     * Deletes the feedback data with the specified ID.
     *
     * @param id the ID of the feedback data to delete.
     */

    public void deleteFeedback(String id) {
        feedbackRepository.deleteById(id);
    }

    /**
     * Retrieves the feedback data with the specified ID.
     *
     * @param id the ID of the feedback data to retrieve.
     * @return the feedback data with the specified ID, or null if not found.
     */
    public FeedbackData getFeedback(String id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves all feedback data.
     *
     * @return a list of all feedback data.
     */
    public List<FeedbackData> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}

