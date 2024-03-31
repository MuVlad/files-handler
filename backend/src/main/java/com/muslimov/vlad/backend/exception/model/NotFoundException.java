package com.muslimov.vlad.backend.exception.model;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String massage) {
        super(massage);
    }
}