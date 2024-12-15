package com.pierluca.recommendation.repository;

import com.pierluca.recommendation.entity.Genre;
import com.pierluca.recommendation.entity.Interaction;
import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.entity.User;
import com.pierluca.recommendation.projection.InteractionRatingOnlyProjection;
import com.pierluca.recommendation.projection.InteractionViewOnlyProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

@DataJpaTest
public class InteractionRepositoryTest {
    private final InteractionRepository interactionRepository;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Autowired
    public InteractionRepositoryTest(InteractionRepository repository,
                                     GenreRepository genreRepository,
                                     MovieRepository movieRepository,
                                     UserRepository userRepository) {
        this.interactionRepository = repository;
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Test
    public void testFindByUserId() {
        User alice = userRepository.save(new User("Alice"));
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));

        Interaction interaction = interactionRepository.save(new Interaction(
                alice,
                toyStory,
                5,
                85
        ));

        List<Interaction> result = interactionRepository.findByUserId(alice.getId());

        assertEquals(1, result.size());
        assertEquals(alice.getId(), result.getFirst().getUser().getId());
        assertEquals(toyStory.getId(), result.getFirst().getMovie().getId());
        assertEquals(interaction.getRating(), result.getFirst().getRating());
        assertEquals(interaction.getViewPercentage(), result.getFirst().getViewPercentage());
    }

    @Test
    public void testFindRatingsOnlyByUserId() {
        User alice = userRepository.save(new User("Alice"));
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));

        Interaction interaction = interactionRepository.save(new Interaction(
                alice,
                toyStory,
                5,
                85
        ));

        List<InteractionRatingOnlyProjection> result = interactionRepository.findRatingsOnlyByUserId(alice.getId());

        assertEquals(1, result.size());
        assertEquals(alice.getId(), result.getFirst().getUser().getId());
        assertEquals(toyStory.getId(), result.getFirst().getMovie().getId());
        assertEquals(interaction.getRating(), result.getFirst().getRating());
    }

    @Test
    public void testFindViewOnlyByUserId() {
        User alice = userRepository.save(new User("Alice"));
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));

        Interaction interaction = interactionRepository.save(new Interaction(
                alice,
                toyStory,
                5,
                85
        ));

        List<InteractionViewOnlyProjection> result = interactionRepository.findViewsOnlyByUserId(alice.getId());

        assertEquals(1, result.size());
        assertEquals(alice.getId(), result.getFirst().getUser().getId());
        assertEquals(toyStory.getId(), result.getFirst().getMovie().getId());
        assertEquals(interaction.getViewPercentage(), result.getFirst().getViewPercentage());
    }

    @Test
    public void testFindUserIdAndMovie() {
        User alice = userRepository.save(new User("Alice"));
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));

        Interaction interaction = interactionRepository.save(new Interaction(
                alice,
                toyStory,
                5,
                85
        ));

        Interaction result = interactionRepository.findByUserAndMovie(alice, toyStory);

        assertEquals(alice.getId(), result.getUser().getId());
        assertEquals(toyStory.getId(), result.getMovie().getId());
        assertEquals(interaction.getRating(), result.getRating());
        assertEquals(interaction.getViewPercentage(), result.getViewPercentage());
    }

    @Test
    public void testFindByUser() {
        User alice = userRepository.save(new User("Alice"));
        Genre adventure = genreRepository.save(new Genre("adventure", "Adventure"));
        Movie toyStory = movieRepository.save(new Movie("Toy Story", List.of(adventure)));

        Interaction interaction = interactionRepository.save(new Interaction(
                alice,
                toyStory,
                5,
                85
        ));

        List<Interaction> result = interactionRepository.findByUser(alice);

        assertEquals(alice.getId(), result.getFirst().getUser().getId());
        assertEquals(toyStory.getId(), result.getFirst().getMovie().getId());
        assertEquals(interaction.getRating(), result.getFirst().getRating());
        assertEquals(interaction.getViewPercentage(), result.getFirst().getViewPercentage());
    }
}
