package com.university.lab04.model;

import com.university.lab04.util.Validation;

import java.time.Year;
import java.util.Objects;

/**
 * Книга: идентификатор, название, автор, год издания.
 * Для корректной работы в HashSet переопределены equals() и hashCode().
 */
public class Book {

    private static final int MIN_YEAR = 0;

    private long id;
    private String title;
    private String author;
    private int year;

    /**
     * @param id     идентификатор
     * @param title  название (не пустое)
     * @param author автор (не пустой)
     * @param year   год издания (от 0 до текущего)
     */
    public Book(long id, String title, String author, int year) {
        this.id = id;
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
        this.title = Validation.requireNotBlank(title, "Название");
    }

    public String getAuthor() {
        return author;
    }

    public final void setAuthor(String author) {
        this.author = Validation.requireNotBlank(author, "Автор");
    }

    public int getYear() {
        return year;
    }

    public final void setYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < MIN_YEAR || year > currentYear) {
            throw new IllegalArgumentException("Некорректный год: " + year);
        }
        this.year = year;
    }

    /** @return строка вида: "Война и мир" — Толстой Л.Н. (1869) */
    public String getDescription() {
        return "\"" + title + "\" — " + author + " (" + year + ")";
    }

    /**
     * Две книги равны, если совпадают название, автор и год издания.
     * Идентификатор не учитывается: одна и та же книга с другим id считается дубликатом.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return year == book.year
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author);
    }

    /** Хеш-код строится по тем же полям, что и equals(). */
    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
