package com.pierluca.recommendation.repository;

import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.entity.Interaction;
import com.pierluca.recommendation.entity.User;
import com.pierluca.recommendation.projection.InteractionRatingOnlyProjection;
import com.pierluca.recommendation.projection.InteractionViewOnlyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    List<Interaction> findByUserId(@Param("userId") Long userId);

    List<InteractionRatingOnlyProjection> findRatingsOnlyByUserId(@Param("userId") Long userId);

    List<InteractionViewOnlyProjection> findViewsOnlyByUserId(@Param("userId") Long userId);

    Interaction findByUserAndMovie(User user, Movie movie);

    List<Interaction> findByUser(User user);
}
