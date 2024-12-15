package com.pierluca.recommendation.dto.response;

import com.pierluca.recommendation.entity.Genre;

public class GenreResponse {
    private String id;
    private String name;

    public GenreResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreResponse(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
