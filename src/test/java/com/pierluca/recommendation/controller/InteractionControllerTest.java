package com.pierluca.recommendation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InteractionControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public InteractionControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetInteractions() throws Exception {
        Long userId = 1L;

        ResultActions result = mockMvc.perform(get("/api/v1/interactions/{userId}", userId));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("Alice"))
                .andExpect(jsonPath("$[0].movie").value("Toy Story"))
                .andExpect(jsonPath("$[0].rating").value(4))
                .andExpect(jsonPath("$[0].view_percentage").value(85))
                .andExpect(jsonPath("$[1].username").value("Alice"))
                .andExpect(jsonPath("$[1].movie").value("Grumpier Old Men"))
                .andExpect(jsonPath("$[1].rating").value(5));
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testAddInteraction() throws Exception{
        Long userId = 1L;
        String json = "{\"movieId\":5,\"rating\":5}";

        ResultActions result = mockMvc.perform(post("/api/v1/interactions/{userId}", userId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("Alice"))
                .andExpect(jsonPath("$.movie").value("The Lion King"))
                .andExpect(jsonPath("$.rating").value(5));
    }
}
