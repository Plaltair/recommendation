package com.pierluca.recommendation.dto.response;

import com.pierluca.recommendation.entity.User;

public class UserResponse {
    private String username;

    public UserResponse(String username) {
        this.username = username;
    }

    public UserResponse(User user) {
        this.username = user.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
