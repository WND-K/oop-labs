package com.university.lab06;

import com.university.lab06.exceptions.BookNotFoundException;
import com.university.lab06.exceptions.EmployeeNotFoundException;
import com.university.lab06.exceptions.InvalidBookDataException;
import com.university.lab06.exceptions.InvalidEmployeeDataException;
import com.university.lab06.model.Book;
import com.university.lab06.model.Employee;
import com.university.lab06.repository.BookRepository;
import com.university.lab06.repository.EmployeeRepository;
import com.university.lab06.service.BookFileService;
import com.university.lab06.service.LogFileService;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Демонстрация обработки исключений и логирования.
 */
public class Main {

    private static final Path BOOKS_FILE = Paths.get("data", "books.txt");
    private static final Path MISSING_FILE = Paths.get("data", "missing.txt");
    private static final Path BROKEN_FILE = Paths.get("data", "books_broken.txt");
    private static final Path SUMMARY_LOG = Paths.get("logs", "summary.log");
    private static final Path APPLICATION_LOG = Paths.get("logs", "application.log");

    public static void main(String[] args) throws IOException {
        BookRepository bookRepository = new BookRepository();

        System.out.println("=== Сохранение книг ===");
        bookRepository.save(new Book("Война и мир", "Толстой Л.Н.", 1869));
        bookRepository.save(new Book("Мастер и Маргарита", "Булгаков М.А.", 1967));
        bookRepository.save(new Book("Преступление и наказание", "Достоевский Ф.М.", 1866));

        System.out.println();
        System.out.println("=== Попытка получения несуществующей книги ===");
        try {
            bookRepository.findById(999);
        } catch (BookNotFoundException e) {
            // checked-исключение: компилятор требует его обработать
            System.out.println("BookNotFoundException: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Удаление книги ===");
        try {
            bookRepository.delete(2);
            bookRepository.delete(2);
        } catch (BookNotFoundException e) {
            System.out.println("BookNotFoundException: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Валидация книги ===");
        try {
            new Book("", "Автор", 2000);
        } catch (InvalidBookDataException e) {
            System.out.println("InvalidBookDataException: " + e.getMessage());
        }
        try {
            new Book("Книга", "Автор", 0);
        } catch (InvalidBookDataException e) {
            System.out.println("InvalidBookDataException: " + e.getMessage());
        }

        demoFiles(bookRepository);
        demoEmployees();
        demoLogFile();
    }

    /** try-with-resources: запись книг в файл, загрузка из него и из некорректных источников. */
    private static void demoFiles(BookRepository repository) throws IOException {
        System.out.println();
        System.out.println("=== Запись и чтение файла (try-with-resources) ===");
        BookFileService fileService = new BookFileService();
        fileService.writeBooks(BOOKS_FILE, repository.findAll().values());
        List<Book> loaded = fileService.loadBooks(BOOKS_FILE);
        System.out.println("Загружено книг из файла: " + loaded.size());

        System.out.println();
        System.out.println("=== Файл с некорректными строками ===");
        Files.write(BROKEN_FILE, List.of(
                "Идиот;Достоевский Ф.М.;1869",
                "Книга без года;Автор",
                "Будущая книга;Автор;3000",
                "Книга;Автор;abc"));
        List<Book> partial = fileService.loadBooks(BROKEN_FILE);
        System.out.println("Загружено книг из файла: " + partial.size() + " (остальные строки пропущены)");

        System.out.println();
        System.out.println("=== Файл не существует ===");
        try {
            fileService.loadBooks(MISSING_FILE);
        } catch (UncheckedIOException e) {
            System.out.println("UncheckedIOException: " + e.getMessage());
        }
    }

    /** Индивидуальное задание (вариант 2): сотрудники. */
    private static void demoEmployees() {
        System.out.println();
        System.out.println("=== Индивидуальное задание, вариант 2: сотрудники ===");
        EmployeeRepository repository = new EmployeeRepository();
        repository.save(new Employee("Иванов И.И.", "инженер", 120000, "Отдел разработки"));
        repository.save(new Employee("Петрова А.С.", "аналитик", 95000, "Отдел данных"));

        try {
            repository.findById(1);
            repository.delete(1);
            repository.findById(1);
        } catch (EmployeeNotFoundException e) {
            System.out.println("EmployeeNotFoundException: " + e.getMessage());
        }

        try {
            new Employee("Козлов К.К.", "менеджер", -5, "Продажи");
        } catch (InvalidEmployeeDataException e) {
            System.out.println("InvalidEmployeeDataException: " + e.getMessage());
        }
        try {
            new Employee("  ", "менеджер", 50000, "Продажи");
        } catch (InvalidEmployeeDataException e) {
            System.out.println("InvalidEmployeeDataException: " + e.getMessage());
        }
    }

    /** try-catch-finally: запись и чтение файла журнала. */
    private static void demoLogFile() throws IOException {
        System.out.println();
        System.out.println("=== Работа с файлом журнала ===");
        LogFileService logService = new LogFileService();
        logService.appendLine(SUMMARY_LOG, "Демонстрация лабораторной работы 6 завершена");
        System.out.println("Последние строки summary.log: " + logService.readLastLines(SUMMARY_LOG, 1));
        System.out.println("Последние строки application.log:");
        logService.readLastLines(APPLICATION_LOG, 2).forEach(line -> System.out.println("  " + line));
    }
}
