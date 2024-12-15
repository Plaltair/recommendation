package com.pierluca.recommendation.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pierluca.recommendation.entity.Interaction;
import com.pierluca.recommendation.projection.InteractionRatingOnlyProjection;
import com.pierluca.recommendation.projection.InteractionViewOnlyProjection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionResponse {
    private String username;
    private String movie;
    private Integer rating;
    @JsonProperty("view_percentage")
    private Integer viewPercentage;

    public InteractionResponse(String username, String movie, Integer rating, Integer viewPercentage) {
        this.username = username;
        this.movie = movie;
        this.rating = rating;
        this.viewPercentage = viewPercentage;
    }

    public InteractionResponse(Interaction interaction) {
        this.username = interaction.getUser().getUsername();
        this.movie = interaction.getMovie().getTitle();
        this.rating = interaction.getRating();
        this.viewPercentage = interaction.getViewPercentage();
    }

    public InteractionResponse(InteractionRatingOnlyProjection rating) {
        this.username = rating.getUser().getUsername();
        this.movie = rating.getMovie().getTitle();
        this.rating = rating.getRating();
    }

    public InteractionResponse(InteractionViewOnlyProjection rating) {
        this.username = rating.getUser().getUsername();
        this.movie = rating.getMovie().getTitle();
        this.viewPercentage = rating.getViewPercentage();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
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
