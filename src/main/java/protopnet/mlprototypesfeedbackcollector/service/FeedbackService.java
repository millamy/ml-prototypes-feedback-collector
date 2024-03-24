package protopnet.mlprototypesfeedbackcollector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.repository.FeedbackRepository;

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

}

