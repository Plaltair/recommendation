package com.pierluca.recommendation.controller;

import com.pierluca.recommendation.dto.request.InteractionRequest;
import com.pierluca.recommendation.dto.response.InteractionResponse;
import com.pierluca.recommendation.service.InteractionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/interactions")
public class InteractionController {
    private final InteractionService service;

    @Autowired
    public InteractionController(InteractionService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public Stream<InteractionResponse> getInteractions(@PathVariable Long userId,
                                                  @RequestParam(required = false) String eventType) {
        if (eventType == null) {
            return service.findInteractions(userId);
        }
        else if (eventType.equals("view")) {
            return service.findPercentageOnly(userId)
                    .filter((e) -> e.getViewPercentage() != null);
        }
        else if (eventType.equals("rating")) {
            return service.findRatingOnly(userId)
                    .filter((e) -> e.getRating() != null);
        }
        return service.findInteractions(userId);
    }

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public InteractionResponse addInteraction(@PathVariable Long userId, @Valid @RequestBody InteractionRequest interaction) {
        return service.addInteraction(userId, interaction);
    }
}
