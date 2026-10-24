package com.university.lab07.exceptions;

/**
 * Типы ошибок, которые могут возникнуть при разборе строки CSV.
 */
public enum ErrorType {
    NOT_ENOUGH_FIELDS("Недостаточно полей"),
    TOO_MANY_FIELDS("Слишком много полей"),
    NON_NUMERIC_ID("Нечисловой id"),
    NON_NUMERIC_YEAR("Нечисловой год"),
    INVALID_YEAR_RANGE("Год вне допустимого диапазона"),
    EMPTY_TITLE("Пустое название"),
    EMPTY_ISBN("Пустой ISBN"),
    NON_NUMERIC_SALARY("Нечисловая зарплата"),
    NON_POSITIVE_SALARY("Зарплата не больше нуля"),
    EMPTY_FULL_NAME("Пустое ФИО"),
    INVALID_DATE("Некорректная дата");

    private final String label;

    ErrorType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
