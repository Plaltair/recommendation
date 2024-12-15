package com.pierluca.recommendation.service;

import com.pierluca.recommendation.dto.response.GenreResponse;
import com.pierluca.recommendation.entity.Genre;
import com.pierluca.recommendation.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @InjectMocks
    private GenreService service;

    @Mock
    private GenreRepository repository;

    @Test
    public void testGetGenres() {
        List<Genre> genres = List.of(
                new Genre("adventure", "Adventure"),
                new Genre("animation", "Animation")
        );

        when(repository.findAll()).thenReturn(genres);

        List<GenreResponse> result = service.getGenres().toList();

        assertEquals(2, result.size());
        assertEquals("adventure", result.getFirst().getId());
        assertEquals("animation", result.get(1).getId());

        verify(repository).findAll();
    }
}
