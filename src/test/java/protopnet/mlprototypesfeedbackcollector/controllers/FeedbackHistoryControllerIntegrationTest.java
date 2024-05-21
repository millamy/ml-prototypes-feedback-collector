package protopnet.mlprototypesfeedbackcollector.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.service.FeedbackService;
import protopnet.mlprototypesfeedbackcollector.util.ImageUtil;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackHistoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Test
    @WithMockUser
    public void testGetAllFeedbacks() throws Exception {
        FeedbackData feedback = new FeedbackData();
        feedback.setOriginalImage(ImageUtil.convertBase64ToBinary("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA"));
        feedback.setPrototypeImage(ImageUtil.convertBase64ToBinary("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA"));

        when(feedbackService.getAllFeedbacks()).thenReturn(Collections.singletonList(feedback));

        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("History"))
                .andExpect(model().attributeExists("feedbacks"));
    }
}
