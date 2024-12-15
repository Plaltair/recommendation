package com.pierluca.recommendation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MoviesControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public MoviesControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testGetMovies() throws Exception {
        String genres = "adventure";
        Integer minRating = 4;

        ResultActions result = mockMvc.perform(get("/api/v1/movies?genres={genres}&minRating={minRating}", genres, minRating));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Toy Story"))
                .andExpect(jsonPath("$[0].genres.length()").value(5))
                .andExpect(jsonPath("$[0].genres[0]").value("Adventure"))
                .andExpect(jsonPath("$[0].genres[1]").value("Animation"))
                .andExpect(jsonPath("$[0].genres[2]").value("Children"))
                .andExpect(jsonPath("$[0].genres[3]").value("Comedy"))
                .andExpect(jsonPath("$[0].genres[4]").value("Fantasy"));
    }

    @Test
    public void testSearchMovies() throws Exception {
        String title = "toy";
        String genres = "adventure";

        ResultActions result = mockMvc.perform(get("/api/v1/movies/search?title={title}&genres={genres}", title, genres));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Toy Story"))
                .andExpect(jsonPath("$[0].genres.length()").value(5))
                .andExpect(jsonPath("$[0].genres[0]").value("Adventure"))
                .andExpect(jsonPath("$[0].genres[1]").value("Animation"))
                .andExpect(jsonPath("$[0].genres[2]").value("Children"))
                .andExpect(jsonPath("$[0].genres[3]").value("Comedy"))
                .andExpect(jsonPath("$[0].genres[4]").value("Fantasy"));
    }
}
