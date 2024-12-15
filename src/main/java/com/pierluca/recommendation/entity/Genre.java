package com.pierluca.recommendation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Table(name = "genre")
@Entity
public class Genre {

    @Id
    @Column(name = "genre_id")
    private String id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Column(unique = true)
    private String name;

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre() {}

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
