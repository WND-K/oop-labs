package com.university.lab07;

import com.university.lab07.io.CsvWriter;
import com.university.lab07.model.Book;
import com.university.lab07.model.Employee;
import com.university.lab07.parser.BookMapper;
import com.university.lab07.parser.CsvLoader;
import com.university.lab07.parser.CsvRecordMapper;
import com.university.lab07.parser.EmployeeMapper;
import com.university.lab07.parser.LoadResult;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Чтение CSV: основное задание (книги) и индивидуальное (сотрудники, вариант 2).
 */
public class Main {

    private static final Path BOOKS_INPUT = Paths.get("src", "main", "resources", "books.csv");
    private static final Path BOOKS_OUTPUT = Paths.get("output", "books_clean.csv");
    private static final Path EMPLOYEES_INPUT = Paths.get("src", "main", "resources", "employees.csv");
    private static final Path EMPLOYEES_OUTPUT = Paths.get("output", "employees_clean.csv");
    private static final String DEPARTMENT_FILTER = "Отдел разработки";

    public static void main(String[] args) {
        try {
            processBooks();
            System.out.println();
            processEmployees();
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }

    private static void processBooks() throws IOException {
        System.out.println("=== Чтение CSV-файла ===");
        System.out.println("Файл: " + BOOKS_INPUT.getFileName());
        System.out.println();

        BookMapper mapper = new BookMapper();
        LoadResult<Book> result = compareApproaches(BOOKS_INPUT, mapper, "Дубликатов по ISBN");

        CsvWriter.write(BOOKS_OUTPUT, mapper, result.items());
        System.out.println("Результат сохранён в: " + BOOKS_OUTPUT.toString().replace('\\', '/'));
    }

    private static void processEmployees() throws IOException {
        System.out.println("=== Индивидуальное задание, вариант 2: сотрудники ===");
        System.out.println("Файл: " + EMPLOYEES_INPUT.getFileName());
        System.out.println();

        EmployeeMapper mapper = new EmployeeMapper();
        LoadResult<Employee> result = compareApproaches(EMPLOYEES_INPUT, mapper, null);

        List<Employee> filtered = result.items().stream()
                .filter(e -> DEPARTMENT_FILTER.equals(e.department()))
                .sorted(Comparator.comparing(Employee::fullName))
                .collect(Collectors.toList());
        System.out.println("Фильтр по отделу «" + DEPARTMENT_FILTER + "»: " + filtered.size() + " сотрудников");
        filtered.stream().limit(5).forEach(e -> System.out.printf(Locale.US,
                "  %s — %s, %.2f руб., принят %s%n", e.fullName(), e.position(), e.salary(), e.hireDate()));
        System.out.println("  ...");
        System.out.println();

        CsvWriter.write(EMPLOYEES_OUTPUT, mapper, result.items());
        System.out.println("Результат сохранён в: " + EMPLOYEES_OUTPUT.toString().replace('\\', '/'));
    }

    /**
     * Читает файл обоими способами, выводит статистику и время, возвращает результат первого способа.
     */
    private static <T> LoadResult<T> compareApproaches(Path input, CsvRecordMapper<T> mapper,
                                                       String duplicateLabel) throws IOException {
        long start = System.nanoTime();
        LoadResult<T> byReader = CsvLoader.loadWithBufferedReader(input, mapper);
        double readerMs = (System.nanoTime() - start) / 1_000_000.0;

        start = System.nanoTime();
        LoadResult<T> byStreams = CsvLoader.loadWithStreams(input, mapper);
        double streamsMs = (System.nanoTime() - start) / 1_000_000.0;

        System.out.println("1. BufferedReader:");
        System.out.print(byReader.statistics().format(duplicateLabel));
        System.out.printf(Locale.US, "Время: %.2f мс%n%n", readerMs);

        System.out.println("2. Files.readAllLines() + Stream API:");
        System.out.print(byStreams.statistics().format(duplicateLabel));
        System.out.printf(Locale.US, "Время: %.2f мс%n%n", streamsMs);

        boolean same = byReader.items().equals(byStreams.items());
        System.out.println("Результаты обоих способов совпадают: " + same);
        System.out.println("Время ориентировочное: первый замер включает прогрев JVM.");
        System.out.println("BufferedReader: просто и понятно, подходит для больших файлов.");
        System.out.println("Files.readAllLines(): удобно, но загружает весь файл в память.");
        System.out.println();
        return byReader;
    }
}
