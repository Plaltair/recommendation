package com.pierluca.recommendation.dto.response;

import com.pierluca.recommendation.entity.Genre;
import com.pierluca.recommendation.entity.Movie;

import java.util.List;

public class MovieResponse {
    private String title;
    private List<String> genres;

    public MovieResponse(String title, List<String> genres) {
        this.title = title;
        this.genres = genres;
    }

    public MovieResponse(Movie movie) {
        this.title = movie.getTitle();
        this.genres = movie.getGenres().stream().map(Genre::getName).toList();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
