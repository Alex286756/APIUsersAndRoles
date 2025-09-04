package ru.kuksov.testproject.exception;

/**
 * Класс формализованного сообщения об ошибке
 * @param message сообщение об ошибке
 * @param statusCode код ошибки
 */
public record ErrorResponse(String message, int statusCode) {
}
