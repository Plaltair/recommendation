package com.pierluca.recommendation.controller;

import com.pierluca.recommendation.dto.response.GenreResponse;
import com.pierluca.recommendation.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/genres")
public class GenreController {
    private final GenreService service;

    @Autowired
    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping
    public Stream<GenreResponse> getGenres() {
        return service.getGenres();
    }
}
