package com.pierluca.recommendation.service;
import com.pierluca.recommendation.dto.response.MovieResponse;
import com.pierluca.recommendation.entity.Genre;
import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService service;

    @Mock
    private MovieRepository repository;

    @Test
    public void testGetGenres() {
        List<Genre> genres = List.of(
                new Genre("adventure", "Adventure"),
                new Genre("animation", "Animation")
        );

        List<Movie> movies = List.of(
                new Movie("Toy Story", List.of(
                        genres.getFirst(),
                        genres.get(1)
                )),
                new Movie("Grumpier Old Men", List.of(
                        genres.getFirst()
                ))
        );

        when(repository.getMovies(List.of(genres.getFirst().getId()), 4, null)).thenReturn(movies);

        List<MovieResponse> result = service.getMovies(List.of(genres.getFirst().getId()), 4, null).toList();

        assertEquals(2, result.size());
        assertEquals(movies.getFirst().getTitle(), result.getFirst().getTitle());
        assertEquals(movies.get(1).getTitle(), result.get(1).getTitle());

        verify(repository).getMovies(List.of(genres.getFirst().getId()), 4, null);
    }

    @Test
    public void testSearchMovies() {
        List<Genre> genres = List.of(
                new Genre("adventure", "Adventure"),
                new Genre("animation", "Animation")
        );

        List<Movie> movies = List.of(
                new Movie("Toy Story", List.of(
                        genres.getFirst(),
                        genres.get(1)
                )),
                new Movie("Grumpier Old Men", List.of(
                        genres.getFirst()
                ))
        );

        when(repository.findByTitleContaining("Toy")).thenReturn(List.of(movies.getFirst()));

        List<MovieResponse> result = service.searchMovies("Toy", null).toList();

        assertEquals(1, result.size());
        assertEquals(movies.getFirst().getTitle(), result.getFirst().getTitle());

        verify(repository).findByTitleContaining("Toy");
    }
}
