package com.pierluca.recommendation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Table(name = "interaction")
@Entity
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Max(5)
    @Min(1)
    private Integer rating;

    @Min(0)
    @Max(100)
    @Column(name = "view_percentage")
    private Integer viewPercentage;

    public Interaction(User user, Movie movie, Integer rating, Integer viewPercentage) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
        this.viewPercentage = viewPercentage;
    }

    public Interaction() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Integer getViewPercentage() {
        return viewPercentage;
    }

    public void setViewPercentage(int viewPercentage) {
        this.viewPercentage = viewPercentage;
    }
}
