package com.university.lab06.exceptions;

/**
 * Checked-исключение: сотрудник с указанным идентификатором не найден.
 */
public class EmployeeNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
