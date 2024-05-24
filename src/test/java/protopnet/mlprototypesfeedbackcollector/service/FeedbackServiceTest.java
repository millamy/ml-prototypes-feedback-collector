package protopnet.mlprototypesfeedbackcollector.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.repository.FeedbackRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the FeedbackService.
 */
public class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    public FeedbackServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for saving feedback.
     */
    @Test
    public void testSaveFeedback() {
        FeedbackData feedback = new FeedbackData();

        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        FeedbackData savedFeedback = feedbackService.saveFeedback(feedback);

        assertNotNull(savedFeedback);
        verify(feedbackRepository, times(1)).save(feedback);
    }

    /**
     * Test for deleting feedback.
     */
    @Test
    public void testDeleteFeedback() {
        String feedbackId = "testId";

        doNothing().when(feedbackRepository).deleteById(feedbackId);

        feedbackService.deleteFeedback(feedbackId);

        verify(feedbackRepository, times(1)).deleteById(feedbackId);
    }

    /**
     * Test for retrieving feedback by ID.
     */
    @Test
    public void testGetFeedback() {
        String feedbackId = "testId";
        FeedbackData feedback = new FeedbackData();

        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedback));

        FeedbackData foundFeedback = feedbackService.getFeedback(feedbackId);

        assertNotNull(foundFeedback);
        verify(feedbackRepository, times(1)).findById(feedbackId);
    }

    /**
     * Test for retrieving all feedbacks.
     */
    @Test
    public void testGetAllFeedbacks() {
        FeedbackData feedback = new FeedbackData();

        when(feedbackRepository.findAll()).thenReturn(Collections.singletonList(feedback));

        List<FeedbackData> feedbackList = feedbackService.getAllFeedbacks();

        assertNotNull(feedbackList);
        assertFalse(feedbackList.isEmpty());
        verify(feedbackRepository, times(1)).findAll();
    }
}
