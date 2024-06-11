package protopnet.mlprototypesfeedbackcollector.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Integration tests for the HomeController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test for displaying the home page.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    @WithMockUser
    public void testShowHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("HomePage"));
    }

    /**
     * Test for displaying the home page for registered users.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    @WithMockUser
    public void testShowHomePageForRegisteredUser() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("HomePageForRegisteredUser"));
    }

    /**
     * Test for displaying the About Us page.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    @WithMockUser
    public void testShowAboutUs() throws Exception {
        mockMvc.perform(get("/about-us"))
                .andExpect(status().isOk())
                .andExpect(view().name("AboutUs"));
    }
}
