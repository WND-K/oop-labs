package com.university.lab04.service;

import com.university.lab04.model.Book;
import com.university.lab04.util.CollectionBenchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Задание на паре: коллекции и Stream API на примере класса Book.
 */
public class BookDemo {

    private static final int FILTER_YEAR = 2000;
    private static final int BENCHMARK_SIZE = 100_000;
    private static final int BENCHMARK_YEAR_RANGE = 100;
    private static final int BENCHMARK_BASE_YEAR = 1900;
    private static final int BENCHMARK_AUTHORS = 1000;

    public void run() {
        List<Book> books = createBooks();
        demoList(books);
        demoSet(books);
        demoMap(books);
        demoStreams(books);
        demoBenchmark();
    }

    private List<Book> createBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Война и мир", "Толстой Л.Н.", 1869));
        books.add(new Book(2, "Анна Каренина", "Толстой Л.Н.", 1877));
        books.add(new Book(3, "Мастер и Маргарита", "Булгаков М.А.", 1967));
        books.add(new Book(4, "Собачье сердце", "Булгаков М.А.", 1925));
        books.add(new Book(5, "Преступление и наказание", "Достоевский Ф.М.", 1866));
        books.add(new Book(6, "Идиот", "Достоевский Ф.М.", 1869));
        books.add(new Book(7, "Чистый код", "Мартин Р.", 2008));
        books.add(new Book(8, "Java. Эффективное программирование", "Блох Дж.", 2018));
        books.add(new Book(9, "Грокаем алгоритмы", "Бхаргава А.", 2016));
        books.add(new Book(10, "Евгений Онегин", "Пушкин А.С.", 1833));
        return books;
    }

    private void demoList(List<Book> books) {
        System.out.println("=== List: сортировка и фильтрация ===");

        List<Book> byYear = new ArrayList<>(books);
        Collections.sort(byYear, Comparator.comparingInt(Book::getYear));
        System.out.println("По году:");
        byYear.forEach(b -> System.out.println("  " + b));

        List<Book> byAuthor = new ArrayList<>(books);
        byAuthor.sort(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle));
        System.out.println("По автору (затем по названию):");
        byAuthor.forEach(b -> System.out.println("  " + b));

        List<Book> modern = new ArrayList<>();
        for (Book book : books) {
            if (book.getYear() > FILTER_YEAR) {
                modern.add(book);
            }
        }
        System.out.println("Книги после " + FILTER_YEAR + " года:");
        modern.forEach(b -> System.out.println("  " + b));
    }

    private void demoSet(List<Book> books) {
        System.out.println();
        System.out.println("=== Set: проверка дубликатов ===");
        Set<Book> uniqueBooks = new HashSet<>(books);
        System.out.println("Уникальных книг: " + uniqueBooks.size());

        // Та же книга, но с другим id: equals() и hashCode() не учитывают id
        Book duplicate = new Book(99, "Война и мир", "Толстой Л.Н.", 1869);
        boolean added = uniqueBooks.add(duplicate);
        System.out.println("Дубликат добавлен: " + added);
        System.out.println("Размер множества после попытки: " + uniqueBooks.size());
    }

    private void demoMap(List<Book> books) {
        System.out.println();
        System.out.println("=== Map: группировка по автору ===");
        Map<String, List<Book>> byAuthor = new HashMap<>();
        for (Book book : books) {
            byAuthor.computeIfAbsent(book.getAuthor(), k -> new ArrayList<>()).add(book);
        }
        // TreeMap — только чтобы вывод шёл в алфавитном порядке
        new TreeMap<>(byAuthor).forEach((author, list) ->
                System.out.println(author + ": " + list.size() + " шт."));
    }

    private void demoStreams(List<Book> books) {
        System.out.println();
        System.out.println("=== Stream API ===");

        List<String> titles = books.stream()
                .filter(b -> b.getYear() > FILTER_YEAR)
                .sorted(Comparator.comparing(Book::getTitle))
                .map(Book::getTitle)
                .collect(Collectors.toList());
        System.out.println("Названия книг после " + FILTER_YEAR + ": " + titles);

        Map<String, Long> countByAuthor = books.stream()
                .collect(Collectors.groupingBy(Book::getAuthor, TreeMap::new, Collectors.counting()));
        System.out.println("Количество книг по авторам: " + countByAuthor);

        Map<Boolean, List<String>> split = books.stream()
                .collect(Collectors.partitioningBy(b -> b.getYear() < 1900,
                        Collectors.mapping(Book::getTitle, Collectors.toList())));
        System.out.println("До 1900 года: " + split.get(true).size() + " книг, после: "
                + split.get(false).size());
    }

    private void demoBenchmark() {
        System.out.println();
        System.out.println("=== Производительность: ArrayList и HashSet ===");
        CollectionBenchmark.compareListAndSet(BENCHMARK_SIZE, i -> new Book(i,
                "Книга " + i,
                "Автор " + (i % BENCHMARK_AUTHORS),
                BENCHMARK_BASE_YEAR + (i % BENCHMARK_YEAR_RANGE)));
    }
}
