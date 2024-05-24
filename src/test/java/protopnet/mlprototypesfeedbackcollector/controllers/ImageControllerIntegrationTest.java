package protopnet.mlprototypesfeedbackcollector.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Integration tests for the ImageController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ImageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${local_analysis.static.path}")
    private String LOCAL_ANALYSIS_PATH;

    @Value("${modeldir.static.path}")
    private String MODELDIR_PATH;

    @Value("${model.static.path}")
    private String MODEL_PATH;

    @Value("${images_direct.static.path}")
    private String STATIC_IMAGES_PATH;

    /**
     * Test for displaying the bird selection page.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    @WithMockUser
    public void testShowBirdSelection() throws Exception {
        File birdPictureFolder = new File(STATIC_IMAGES_PATH);
        if (!birdPictureFolder.exists()) {
            birdPictureFolder.mkdir();
        }

        mockMvc.perform(get("/picture-selection"))
                .andExpect(status().isOk())
                .andExpect(view().name("PictureSelection"));
    }

    /**
     * Test for analyzing selected pictures successfully.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    @WithMockUser
    public void testAnalyzeSelectedPictures_Success() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("currentImageIndex", 0);
        session.setAttribute("selectedImageUrls", STATIC_IMAGES_PATH + "032.Mangrove_Cuckoo/Mangrove_Cuckoo_0005_794599.jpg;" + STATIC_IMAGES_PATH + "032.Mangrove_Cuckoo/Mangrove_Cuckoo_0006_794600.jpg");

        mockMvc.perform(post("/selected-pictures")
                        .session(session)
                        .param("selectedImageUrl", STATIC_IMAGES_PATH + "032.Mangrove_Cuckoo/Mangrove_Cuckoo_0005_794599.jpg;" + STATIC_IMAGES_PATH + "032.Mangrove_Cuckoo/Mangrove_Cuckoo_0006_794600.jpg")
                        .param("birdKind", "Mangrove Cuckoo"))
                .andExpect(status().isOk())
                .andExpect(view().name("Results"));
    }

    /**
     * Test for analyzing the next image.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    @WithMockUser
    public void testAnalyzeNextImage() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("currentImageIndex", 0);
        session.setAttribute("selectedImageUrls", "/Users/aleksandragrygorczyk/Documents/ProtoPNet/CUB_200_2011/test_cropped/032.Mangrove_Cuckoo/Mangrove_Cuckoo_0005_794599.jpg;/Users/aleksandragrygorczyk/Documents/ProtoPNet/CUB_200_2011/test_cropped/032.Mangrove_Cuckoo/Mangrove_Cuckoo_0006_794600.jpg");

        mockMvc.perform(get("/analyze-next")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("Results"));
    }

}
