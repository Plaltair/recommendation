package com.pierluca.recommendation.repository;

import com.pierluca.recommendation.entity.Genre;
import com.pierluca.recommendation.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("""
    SELECT m FROM Movie m
    LEFT JOIN m.genres g
    LEFT JOIN Interaction r ON r.movie = m
    WHERE (:genres IS NULL OR g.id IN :genres)
    AND (:minRating IS NULL OR
          (SELECT AVG(int.rating) FROM Interaction int WHERE int.movie = m) >= :minRating)
    AND (:maxRating IS NULL OR
          (SELECT AVG(int.rating) FROM Interaction int WHERE int.movie = m) <= :maxRating)
    """)
    List<Movie> getMovies(@Param("genres") List<String> genres,
                           @Param("minRating") Integer minRating,
                           @Param("maxRating") Integer maxRating);

    List<Movie> findByGenresInAndIdNotIn(Set<Genre> genres, Set<Long> ids);

    List<Movie> findByTitleContaining(String title);

    List<Movie> findByGenresIdIn(List<String> genres);

    List<Movie> findByTitleContainingAndGenresIdIn(String title, List<String> genres);
}
