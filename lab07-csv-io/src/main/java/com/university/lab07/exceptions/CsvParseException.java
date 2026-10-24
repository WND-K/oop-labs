package com.university.lab07.exceptions;

/**
 * Checked-исключение: строка CSV не может быть превращена в объект.
 * Содержит тип ошибки для подсчёта статистики.
 */
public class CsvParseException extends Exception {

    private static final long serialVersionUID = 1L;

    private final ErrorType type;

    public CsvParseException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public CsvParseException(ErrorType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }
}
