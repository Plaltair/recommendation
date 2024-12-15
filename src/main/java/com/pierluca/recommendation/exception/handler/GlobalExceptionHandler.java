package com.pierluca.recommendation.exception.handler;

import com.pierluca.recommendation.dto.error.ArgumentNotValidResponse;
import com.pierluca.recommendation.exception.MovieNotFoundException;
import com.pierluca.recommendation.exception.MultipleEventException;
import com.pierluca.recommendation.exception.RecommendationsNotAvailableException;
import com.pierluca.recommendation.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ArgumentNotValidResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<ArgumentNotValidResponse> errors = new ArrayList<>();

        for (FieldError error : bindingResult.getFieldErrors()) {
            final ArgumentNotValidResponse e = new ArgumentNotValidResponse(error.getField(), error.getDefaultMessage());
            errors.add(e);
        }

        return errors;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
        return Map.ofEntries(
                Map.entry("error", ex.getMessage())
        );
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleMovieNotFoundException(MovieNotFoundException ex) {
        return Map.ofEntries(
                Map.entry("error", ex.getMessage())
        );
    }

    @ExceptionHandler(MultipleEventException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMultipleEventException(MultipleEventException ex) {
        return Map.ofEntries(
                Map.entry("error", ex.getMessage())
        );
    }

    @ExceptionHandler(RecommendationsNotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleRecommendationsNotAvailableException(RecommendationsNotAvailableException ex) {
        return Map.ofEntries(
                Map.entry("error", ex.getMessage())
        );
    }
}
