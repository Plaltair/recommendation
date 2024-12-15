package com.pierluca.recommendation.service;

import com.pierluca.recommendation.dto.response.MovieResponse;
import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class MovieService {
    private final MovieRepository repository;

    @Autowired
    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public Stream<MovieResponse> getMovies(List<String> genres, Integer minRating, Integer maxRating) {
        return repository.getMovies(genres, minRating, maxRating).stream().map(MovieResponse::new);
    }

    public Stream<MovieResponse> searchMovies(String title, List<String> genres) {
        List<Movie> response;
        if (title != null && genres != null && !genres.isEmpty()) {
            response = repository.findByTitleContainingAndGenresIdIn(title, genres);
        }
        else if (title != null) {
            response = repository.findByTitleContaining(title);
        }
        else if (genres != null) {
            response = repository.findByGenresIdIn(genres);
        }
        else {
            return null;
        }
        return response.stream().map(MovieResponse::new);
    }
}
