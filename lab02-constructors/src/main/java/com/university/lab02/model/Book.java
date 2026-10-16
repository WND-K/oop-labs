package com.university.lab02.model;

import java.time.Year;

/**
 * Книга: название, автор, год издания.
 * Лабораторная работа №2: перегруженные конструкторы, статический счётчик,
 * фабричный метод и перегруженный метод getDescription.
 */
public class Book {

    /** Наименьший допустимый год издания. */
    private static final int MIN_YEAR = 0;

    /** Значения по умолчанию для конструктора без параметров. */
    private static final String DEFAULT_TITLE = "Без названия";
    private static final String DEFAULT_AUTHOR = "Неизвестен";

    /** Счётчик созданных книг (общий для всех объектов класса). */
    private static int counter = 0;

    private long id;
    private String title;
    private String author;
    private int year;

    /**
     * Конструктор по умолчанию: id=0, title="Без названия",
     * author="Неизвестен", year=0.
     */
    public Book() {
        this(0, DEFAULT_TITLE, DEFAULT_AUTHOR, 0);
    }

    /**
     * Основной конструктор. Все остальные конструкторы вызывают его через this(...),
     * поэтому счётчик учитывает все способы создания объекта.
     *
     * @param id     идентификатор
     * @param title  название (не пустое)
     * @param author автор (не пустой)
     * @param year   год издания (от 0 до текущего года)
     */
    public Book(long id, String title, String author, int year) {
        this.id = id;
        setTitle(title);
        setAuthor(author);
        setYear(year);
        counter++; // увеличиваем только если все проверки прошли успешно
    }

    /** Конструктор без id (id=0). */
    public Book(String title, String author, int year) {
        this(0, title, author, year);
    }

    /** Конструктор только с названием и автором (id=0, year=0). */
    public Book(String title, String author) {
        this(0, title, author, 0);
    }

    /**
     * Статический фабричный метод. Создаёт книгу с автоинкрементным id:
     * следующий номер = количество уже созданных книг + 1.
     */
    public static Book createBook(String title, String author, int year) {
        return new Book(counter + 1L, title, author, year);
    }

    /** @return количество созданных книг */
    public static int getCounter() {
        return counter;
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

    /** @throws IllegalArgumentException если название null или пустое */
    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    /** @throws IllegalArgumentException если автор null или пустой */
    public void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Автор не может быть пустым");
        }
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    /** @throws IllegalArgumentException если год вне диапазона [0; текущий год] */
    public void setYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < MIN_YEAR || year > currentYear) {
            throw new IllegalArgumentException("Некорректный год: " + year);
        }
        this.year = year;
    }

    /** @return полное описание: "Война и мир" — Толстой Л.Н. (1869) */
    public String getDescription() {
        return "\"" + title + "\" — " + author + " (" + year + ")";
    }

    /**
     * Перегруженный вариант описания.
     *
     * @param shortFormat true — краткий формат: Война и мир (1869)
     */
    public String getDescription(boolean shortFormat) {
        if (shortFormat) {
            return title + " (" + year + ")";
        }
        return getDescription();
    }

    @Override
    public String toString() {
        return getDescription();
    }
}