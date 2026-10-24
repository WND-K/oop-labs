package com.university.lab06.model;

import com.university.lab06.exceptions.InvalidBookDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;

/**
 * Книга. Валидация выполняется в сеттерах: при нарушении пишется ERROR в лог
 * и выбрасывается InvalidBookDataException.
 */
public class Book {

    private static final Logger logger = LoggerFactory.getLogger(Book.class);

    private long id;
    private String title;
    private String author;
    private int year;

    /**
     * @throws InvalidBookDataException если одно из значений некорректно
     */
    public Book(String title, String author, int year) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            logger.error("Ошибка валидации: title не может быть null или пустым");
            throw new InvalidBookDataException("title не может быть null или пустым");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public final void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            logger.error("Ошибка валидации: author не может быть null или пустым");
            throw new InvalidBookDataException("author не может быть null или пустым");
        }
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public final void setYear(int year) {
        int currentYear = Year.now().getValue();
        if (year <= 0 || year > currentYear) {
            logger.error("Ошибка валидации: year должно быть в диапазоне (0, {}], получено {}", currentYear, year);
            throw new InvalidBookDataException("year должно быть в диапазоне (0, " + currentYear + "]");
        }
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', year=%d}", id, title, author, year);
    }
}
