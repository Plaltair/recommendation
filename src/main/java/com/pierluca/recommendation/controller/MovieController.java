package com.pierluca.recommendation.controller;

import com.pierluca.recommendation.dto.response.MovieResponse;
import com.pierluca.recommendation.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public Stream<MovieResponse> getMovies(@RequestParam(required = false) List<String> genres,
                                               @RequestParam(required = false) Integer minRating,
                                               @RequestParam(required = false) Integer maxRating) {
        return service.getMovies(genres, minRating, maxRating);
    }

    @GetMapping("/search")
    public Stream<MovieResponse> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> genres
    ) {
        return service.searchMovies(title, genres);
    }
}
