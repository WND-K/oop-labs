package com.university.lab06.exceptions;

/**
 * Checked-исключение: книга с указанным идентификатором не найдена.
 * Наследуется от Exception, поэтому его нужно обработать (try-catch) или объявить через throws.
 */
public class BookNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
