package protopnet.mlprototypesfeedbackcollector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.repository.FeedbackRepository;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public FeedbackData saveFeedback(FeedbackData feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(String id) {
        feedbackRepository.deleteById(id);
    }

    public FeedbackData getFeedback(String id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public List<FeedbackData> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

}

