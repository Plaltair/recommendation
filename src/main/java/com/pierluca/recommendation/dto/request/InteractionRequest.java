package com.pierluca.recommendation.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class InteractionRequest {
    @NotNull(message = "movie id is required")
    private Long movieId;

    @Min(value = 1, message = "rating cannot be less than 1")
    @Max(value = 5, message = "rating cannot be more than 5")
    private Integer rating;

    @JsonProperty("view_percentage")
    @Min(value = 0, message = "view percentage cannot be negative")
    @Max(value = 100, message = "view percentage cannot be more than 100")
    private Integer viewPercentage;

    public InteractionRequest(Long movieId, Integer rating, Integer viewPercentage) {
        this.movieId = movieId;
        this.rating = rating;
        this.viewPercentage = viewPercentage;
    }

    public Long  getMovieId() {
        return movieId;
    }

    public void setMovieId(Long  movieId) {
        this.movieId = movieId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getViewPercentage() {
        return viewPercentage;
    }

    public void setViewPercentage(Integer viewPercentage) {
        this.viewPercentage = viewPercentage;
    }
}

