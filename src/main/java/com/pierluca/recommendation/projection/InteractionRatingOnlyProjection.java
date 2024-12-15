package com.pierluca.recommendation.projection;

import com.pierluca.recommendation.entity.Movie;
import com.pierluca.recommendation.entity.User;

public interface InteractionRatingOnlyProjection {
    User getUser();
    Movie getMovie();
    Integer getRating();
}
