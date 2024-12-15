package com.pierluca.recommendation.exception;

public class MultipleEventException extends RuntimeException {
    public MultipleEventException() {
        super("only one event can be requested at a time");
    }
}
