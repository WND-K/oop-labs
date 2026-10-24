package com.university.lab06.exceptions;

/**
 * Unchecked-исключение: некорректные данные книги.
 * Наследуется от RuntimeException, обработка не обязательна.
 */
public class InvalidBookDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidBookDataException(String message) {
        super(message);
    }

    public InvalidBookDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
