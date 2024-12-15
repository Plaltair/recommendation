package com.pierluca.recommendation.exception;

public class RecommendationsNotAvailableException extends RuntimeException {
    public RecommendationsNotAvailableException() {
        super("recommendations not available for this user at this time");
    }
}