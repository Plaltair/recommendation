package com.pierluca.recommendation.repository;
import com.pierluca.recommendation.entity.Genre;
import com.pierluca.recommendation.entity.Interaction;
import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

@DataJpaTest
public class MovieRepositoryTest {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final InteractionRepository interactionRepository;

    @Autowired
    public MovieRepositoryTest(MovieRepository movieRepository,
                               GenreRepository genreRepository,
                               InteractionRepository interactionRepository,
                               UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.interactionRepository = interactionRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void testGetMovies() {
        User alice = userRepository.save(new User("alice"));
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));
        Interaction interaction = interactionRepository.save(new Interaction(
                alice,
                toyStory,
                5,
                85
        ));

        List<Movie> movies = movieRepository.getMovies(List.of("adventure"), interaction.getRating(), null);

        assertEquals(1, movies.size());
        assertEquals(toyStory.getId(), movies.getFirst().getId());
        assertEquals(toyStory.getTitle(), movies.getFirst().getTitle());
    }

    @Test
    public void testFindByGenresIdAndNotInMovies() {
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Genre thriller = genreRepository.save(new Genre("thriller", "Thriller"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));
        Movie lionKing = movieRepository.save(new Movie("The Lion King", List.of(thriller)));

        List<Movie> movies = movieRepository.findByGenresInAndIdNotIn(Set.of(adventure), Set.of(lionKing.getId()));

        assertEquals(1, movies.size());
        assertEquals(toyStory.getId(), movies.getFirst().getId());
        assertEquals(toyStory.getTitle(), movies.getFirst().getTitle());
    }

    @Test
    public void testFindByTitleContaining() {
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Genre thriller = genreRepository.save(new Genre("thriller", "Thriller"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));
        movieRepository.save(new Movie("The Lion King", List.of(thriller)));

        List<Movie> movies = movieRepository.findByTitleContaining("Toy");

        assertEquals(1, movies.size());
        assertEquals(toyStory.getId(), movies.getFirst().getId());
        assertEquals(toyStory.getTitle(), movies.getFirst().getTitle());
    }

    @Test
    public void testFindByGenresIdIn() {
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        genreRepository.save(new Genre("thriller", "Thriller"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));

        List<Movie> movies = movieRepository.findByGenresIdIn(List.of("adventure"));

        assertEquals(1, movies.size());
        assertEquals(toyStory.getId(), movies.getFirst().getId());
        assertEquals(toyStory.getTitle(), movies.getFirst().getTitle());
    }

    @Test
    public void testFindByTitleContainingAndGenresIdIn() {
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Genre thriller = genreRepository.save(new Genre("thriller", "Thriller"));
        genreRepository.save(new Genre("thriller", "Thriller"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));
        movieRepository.save(new Movie("The Lion King", List.of(thriller)));

        List<Movie> movies = movieRepository.findByTitleContainingAndGenresIdIn("Toy", List.of(adventure.getId()));

        assertEquals(1, movies.size());
        assertEquals(toyStory.getId(), movies.getFirst().getId());
        assertEquals(toyStory.getTitle(), movies.getFirst().getTitle());
    }
}
