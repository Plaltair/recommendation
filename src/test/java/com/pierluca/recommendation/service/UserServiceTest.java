package com.pierluca.recommendation.service;
import com.pierluca.recommendation.dto.response.MovieResponse;
import com.pierluca.recommendation.dto.response.UserResponse;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private MovieRepository movieRepository;

    @Test
    public void testGetUsers() {
        List<User> users = List.of(
                new User("Alice"),
                new User("Bob"),
                new User("Charlie")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<UserResponse> result = service.getUsers().toList();

        assertEquals(3, result.size());
        assertEquals(users.getFirst().getUsername(), result.getFirst().getUsername());
        assertEquals(users.get(1).getUsername(), result.get(1).getUsername());
        assertEquals(users.get(2).getUsername(), result.get(2).getUsername());

        verify(userRepository).findAll();
    }

    @Test
    void testGetRecommendations() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Genre action = new Genre("action", "Action");
        Genre comedy = new Genre("comedy", "Comedy");

        Movie movie1 = new Movie(
                "Toy Story",
                List.of(action)
        );
        movie1.setId(10L);

        Movie movie2 = new Movie(
                "The Lion King",
                List.of(action, comedy)
        );
        movie2.setId(20L);

        Interaction interaction1 = new Interaction(user, movie1, 4, null);
        Interaction interaction2 = new Interaction(user, movie2, 2, null);

        List<Interaction> interactions = List.of(interaction1, interaction2);

        Set<Genre> likedGenres = Set.of(action);
        Set<Long> ratedMovieIds = Set.of(10L, 20L);

        Movie recommendedMovie = new Movie(
                "Jurassic Park",
                List.of(action)
        );
        recommendedMovie.setId(30L);

        List<Movie> recommendedMovies = List.of(recommendedMovie);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(interactionRepository.findByUser(user)).thenReturn(interactions);
        when(movieRepository.findByGenresInAndIdNotIn(likedGenres, ratedMovieIds))
                .thenReturn(recommendedMovies);

        Stream<MovieResponse> result = service.getRecommendations(userId);

        List<MovieResponse> movieResponses = result.toList();
        assertEquals(1, movieResponses.size());
        assertEquals("Jurassic Park", movieResponses.getFirst().getTitle());

        verify(userRepository).findById(userId);
        verify(interactionRepository).findByUser(user);
        verify(movieRepository).findByGenresInAndIdNotIn(likedGenres, ratedMovieIds);
    }

}
