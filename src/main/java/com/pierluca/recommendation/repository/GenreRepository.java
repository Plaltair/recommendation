package com.pierluca.recommendation.repository;

import com.pierluca.recommendation.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, String> {
}
