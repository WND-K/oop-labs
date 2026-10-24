package com.university.lab06.service;

import com.university.lab06.exceptions.InvalidBookDataException;
import com.university.lab06.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Запись книг в текстовый файл и загрузка из него (try-with-resources).
 * Формат строки файла: название;автор;год
 */
public class BookFileService {

    private static final Logger logger = LoggerFactory.getLogger(BookFileService.class);
    private static final String SEPARATOR = ";";
    private static final int FIELDS_COUNT = 3;

    /**
     * Записывает книги в файл. Writer закрывается автоматически (try-with-resources).
     *
     * @throws IOException при ошибке записи
     */
    public void writeBooks(Path path, Collection<Book> books) throws IOException {
        logger.info("Запись книг в файл: {}", path);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            for (Book book : books) {
                writer.write(book.getTitle() + SEPARATOR + book.getAuthor() + SEPARATOR + book.getYear());
                writer.newLine();
            }
        }
        logger.info("Записано книг: {}", books.size());
    }

    /**
     * Загружает книги из файла. Некорректные строки пропускаются и логируются на уровне ERROR.
     *
     * @return успешно загруженные книги
     * @throws UncheckedIOException если файл не найден или не читается
     */
    public List<Book> loadBooks(Path path) {
        logger.info("Загрузка книг из файла: {}", path);
        List<Book> books = new ArrayList<>();
        int successCount = 0;
        int errorCount = 0;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    books.add(parseLine(line));
                    successCount++;
                } catch (InvalidBookDataException | NumberFormatException e) {
                    errorCount++;
                    logger.error("Ошибка при обработке строки: {}", line, e);
                }
            }
        } catch (NoSuchFileException e) {
            logger.error("Файл не найден: {}", path, e);
            throw new UncheckedIOException("Файл не найден: " + path, e);
        } catch (IOException e) {
            logger.error("Ошибка при чтении файла: {}", path, e);
            throw new UncheckedIOException("Ошибка при чтении файла: " + path, e);
        }

        logger.info("Загрузка завершена: успешно={}, ошибок={}", successCount, errorCount);
        return books;
    }

    /**
     * Разбирает одну строку файла.
     *
     * @throws InvalidBookDataException если данные книги некорректны
     * @throws NumberFormatException    если год не является числом
     */
    private Book parseLine(String line) {
        String[] parts = line.split(SEPARATOR, -1);
        if (parts.length != FIELDS_COUNT) {
            throw new InvalidBookDataException("Ожидается " + FIELDS_COUNT + " поля, получено " + parts.length);
        }
        return new Book(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim()));
    }
}
