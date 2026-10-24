package com.university.lab07.model;

/**
 * Книга. Record автоматически создаёт конструктор, геттеры (id(), title() ...),
 * equals(), hashCode() и toString().
 */
public record Book(long id, String title, String author, int year, String isbn, String publisher) {
}
