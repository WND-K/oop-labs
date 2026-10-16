package com.university.lab04.util;

/**
 * Общие проверки значений, чтобы не дублировать их в классах иерархий.
 */
public final class Validation {

    private Validation() {
        // утилитный класс: создавать объекты не нужно
    }

    /** @throws IllegalArgumentException если строка null или пустая */
    public static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
        return value;
    }

    /** @throws IllegalArgumentException если число не больше нуля */
    public static double requirePositive(double value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException("Некорректное значение поля «" + fieldName + "»: " + value + " (должно быть больше 0)");
        }
        return value;
    }

    /** @throws IllegalArgumentException если число меньше нуля */
    public static double requireNonNegative(double value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException("Некорректное значение поля «" + fieldName + "»: " + value + " (должно быть не меньше 0)");
        }
        return value;
    }
}
