package protopnet.mlprototypesfeedbackcollector.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import protopnet.mlprototypesfeedbackcollector.model.FeedbackData;
import protopnet.mlprototypesfeedbackcollector.service.FeedbackService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Test
    @WithMockUser(username = "testuser")
    public void testSaveFeedback_Success() throws Exception {
        FeedbackData feedbackData = new FeedbackData();
        feedbackData.setOriginalImageData("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA");
        feedbackData.setPrototypeImageData("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA");

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        mockMvc.perform(put("/save-feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"originalImageData\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA\",\"prototypeImageData\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testSaveFeedback_Error() throws Exception {
        Mockito.doThrow(new RuntimeException("Error occurred")).when(feedbackService).saveFeedback(Mockito.any(FeedbackData.class));

        mockMvc.perform(put("/save-feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"originalImageData\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA\",\"prototypeImageData\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA\"}]"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Error occurred while saving feedback data."));
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testCurrentUserName() throws Exception {
        mockMvc.perform(get("/username"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("testuser"));
    }
}
