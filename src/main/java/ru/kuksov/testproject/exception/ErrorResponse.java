package ru.kuksov.testproject.exception;

public record ErrorResponse(String message, int statusCode) {
}
