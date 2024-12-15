package com.pierluca.recommendation.controller;

import com.pierluca.recommendation.dto.response.MovieResponse;
import com.pierluca.recommendation.dto.response.UserResponse;
import com.pierluca.recommendation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Stream<UserResponse> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/{userId}/recommendations")
    public Stream<MovieResponse> getRecommendations(@PathVariable Long userId) {
        return service.getRecommendations(userId);
    }
}
