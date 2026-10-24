package com.university.lab06.exceptions;

/**
 * Unchecked-исключение: некорректные данные сотрудника.
 */
public class InvalidEmployeeDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidEmployeeDataException(String message) {
        super(message);
    }

    public InvalidEmployeeDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
