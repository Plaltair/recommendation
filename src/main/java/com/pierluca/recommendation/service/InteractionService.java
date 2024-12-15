package com.pierluca.recommendation.service;

import com.pierluca.recommendation.dto.request.InteractionRequest;
import com.pierluca.recommendation.dto.response.InteractionResponse;
import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.entity.Interaction;
import com.pierluca.recommendation.entity.User;
import com.pierluca.recommendation.exception.MovieNotFoundException;
import com.pierluca.recommendation.exception.MultipleEventException;
import com.pierluca.recommendation.exception.UserNotFoundException;
import com.pierluca.recommendation.projection.InteractionRatingOnlyProjection;
import com.pierluca.recommendation.projection.InteractionViewOnlyProjection;
import com.pierluca.recommendation.repository.MovieRepository;
import com.pierluca.recommendation.repository.InteractionRepository;
import com.pierluca.recommendation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class InteractionService {
    private final InteractionRepository interactionRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public InteractionService(InteractionRepository repository, UserRepository userRepository, MovieRepository movieRepository) {
        this.interactionRepository = repository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public Stream<InteractionResponse> findInteractions(Long userId) {
        final List<Interaction> ratings = interactionRepository.findByUserId(userId);
        return ratings.stream().map(InteractionResponse::new);
    }

    public Stream<InteractionResponse> findRatingOnly(Long userId) {
        final List<InteractionRatingOnlyProjection> ratings = interactionRepository.findRatingsOnlyByUserId(userId);
        return ratings.stream().map(InteractionResponse::new);
    }

    public Stream<InteractionResponse> findPercentageOnly(Long userId) {
        final List<InteractionViewOnlyProjection> ratings = interactionRepository.findViewsOnlyByUserId(userId);
        return ratings.stream().map(InteractionResponse::new);
    }

    public InteractionResponse addInteraction(Long userId, InteractionRequest interaction) {
        if (interaction.getRating() != null && interaction.getViewPercentage() != null) {
            throw new MultipleEventException();
        }

        final User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        final Movie movie = movieRepository.findById(interaction.getMovieId()).orElseThrow(MovieNotFoundException::new);

        Interaction existing = interactionRepository.findByUserAndMovie(user, movie);
        if (existing != null) {
            if (interaction.getRating() != null) {
                existing.setRating(interaction.getRating());
            }
            if (interaction.getViewPercentage() != null) {
                existing.setViewPercentage(interaction.getViewPercentage());
            }
            return new InteractionResponse(interactionRepository.save(existing));
        }
        final Interaction added = interactionRepository.save(new Interaction(user, movie, interaction.getRating(), interaction.getViewPercentage()));
        return new InteractionResponse(added);
    }
}
