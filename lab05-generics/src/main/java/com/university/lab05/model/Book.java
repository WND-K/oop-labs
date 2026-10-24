package com.university.lab05.model;

/**
 * Простая запись (record) для демонстрации хранилища с собственным типом.
 * Record сам создаёт конструктор, геттеры (title(), year()), equals(), hashCode() и toString().
 */
public record Book(String title, int year) {
}
