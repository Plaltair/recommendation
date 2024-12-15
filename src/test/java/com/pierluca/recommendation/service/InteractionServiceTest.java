package com.pierluca.recommendation.service;

import com.pierluca.recommendation.dto.request.InteractionRequest;
import com.pierluca.recommendation.dto.response.InteractionResponse;
import com.pierluca.recommendation.entity.Genre;
import com.pierluca.recommendation.entity.Interaction;
import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.entity.User;
import com.pierluca.recommendation.repository.InteractionRepository;
import com.pierluca.recommendation.repository.MovieRepository;
import com.pierluca.recommendation.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InteractionServiceTest {

    @InjectMocks
    private InteractionService service;

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindInteractions() {
        final List<Genre> genres = List.of(
                new Genre("adventure", "Adventure"),
                new Genre("animation", "Animation")
        );

        final List<Movie> movies = List.of(
                new Movie("Toy Story", List.of(
                        genres.getFirst(),
                        genres.get(1)
                )),
                new Movie("Grumpier Old Men", List.of(
                        genres.get(1)
                ))
        );

        final List<User> users = List.of(
                new User("Alice"),
                new User("Bob"),
                new User("Charlie")
        );

        final List<Interaction> interactions = List.of(
                new Interaction(users.getFirst(), movies.getFirst(), 4, 85),
                new Interaction(users.getFirst(), movies.get(1), 5, null)
        );

        when(interactionRepository.findByUserId(users.getFirst().getId())).thenReturn(interactions);

        List<InteractionResponse> result = service.findInteractions(users.getFirst().getId()).toList();

        assertEquals(2, result.size());
        assertEquals("Toy Story", result.getFirst().getMovie());
        assertEquals(users.getFirst().getUsername(), result.getFirst().getUsername());
        assertEquals(interactions.getFirst().getRating(), result.getFirst().getRating());
        assertEquals(interactions.getFirst().getViewPercentage(), result.getFirst().getViewPercentage());

        assertEquals("Grumpier Old Men", result.get(1).getMovie());
        assertEquals(users.getFirst().getUsername(), result.get(1).getUsername());
        assertEquals(interactions.get(1).getRating(), result.get(1).getRating());
        assertEquals(interactions.get(1).getViewPercentage(), result.get(1).getViewPercentage());

        verify(interactionRepository).findByUserId(users.getFirst().getId());
    }

    @Test
    public void testAddInteraction() {
        Long userId = 1L;
        Long movieId = 10L;

        User user = new User();
        user.setId(userId);

        Movie movie = new Movie();
        movie.setId(movieId);

        InteractionRequest interactionRequest = new InteractionRequest(movieId, 4,null);

        Interaction existingInteraction = new Interaction(user, movie, 4, null);
        Interaction updatedInteraction = new Interaction(user, movie, 4, 50);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(interactionRepository.findByUserAndMovie(user, movie)).thenReturn(existingInteraction);
        when(interactionRepository.save(existingInteraction)).thenReturn(updatedInteraction);

        InteractionResponse response = service.addInteraction(userId, interactionRequest);

        assertNotNull(response);
        assertEquals(4, response.getRating());
        assertEquals(50, response.getViewPercentage());
        verify(userRepository).findById(userId);
        verify(movieRepository).findById(movieId);
        verify(interactionRepository).findByUserAndMovie(user, movie);
        verify(interactionRepository).save(existingInteraction);
    }
}
