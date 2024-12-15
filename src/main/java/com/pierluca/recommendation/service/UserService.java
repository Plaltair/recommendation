package com.pierluca.recommendation.service;

import com.pierluca.recommendation.dto.response.MovieResponse;
import com.pierluca.recommendation.dto.response.UserResponse;
import com.pierluca.recommendation.entity.Genre;
import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.entity.Interaction;
import com.pierluca.recommendation.entity.User;
import com.pierluca.recommendation.exception.RecommendationsNotAvailableException;
import com.pierluca.recommendation.exception.UserNotFoundException;
import com.pierluca.recommendation.repository.MovieRepository;
import com.pierluca.recommendation.repository.InteractionRepository;
import com.pierluca.recommendation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final MovieRepository movieRepository;

    private final InteractionRepository interactionRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       MovieRepository movieRepository,
                       InteractionRepository interactionRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.interactionRepository = interactionRepository;
    }

    public Stream<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(UserResponse::new);
    }

    public Stream<MovieResponse> getRecommendations(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        List<Interaction> interactions = interactionRepository.findByUser(user);

        Set<Genre> likedGenres = interactions.stream()
                .filter(interaction -> interaction.getRating() != null && interaction.getRating() >= 4)
                .flatMap(interaction -> interaction.getMovie().getGenres().stream())
                .collect(Collectors.toSet());

        Set<Long> ratedMovieIds = interactions.stream()
                .map(interaction -> interaction.getMovie().getId())
                .collect(Collectors.toSet());

        List<Movie> recommendedMovies = movieRepository.findByGenresInAndIdNotIn(likedGenres, ratedMovieIds);

        if (recommendedMovies.isEmpty()) {
            throw new RecommendationsNotAvailableException();
        }

        return recommendedMovies.stream().map(MovieResponse::new);
    }
}
