package com.pierluca.recommendation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetGenres() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/v1/users"));
        result.andExpect(status().isOk());
    }

    @Test
    public void testRecommendations() throws Exception {
        Long userId = 1L;

        ResultActions result = mockMvc.perform(get("/api/v1/users/{userId}/recommendations", userId));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].title").value("Star Wars: Return of the Jedi"))
                .andExpect(jsonPath("$[1].title").value("The Lion King"))
                .andExpect(jsonPath("$[2].title").value("Forrest Gump"))
                .andExpect(jsonPath("$[3].title").value("Jurassic Park"));
    }
}
