package com.pierluca.recommendation.service;

import com.pierluca.recommendation.dto.response.GenreResponse;
import com.pierluca.recommendation.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

@Service
public class GenreService {
    private final GenreRepository repository;

    @Autowired
    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public Stream<GenreResponse> getGenres() {
        return repository.findAll().stream().map(GenreResponse::new);
    }

}
