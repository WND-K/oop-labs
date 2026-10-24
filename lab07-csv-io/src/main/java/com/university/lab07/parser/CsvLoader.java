package com.university.lab07.parser;

import com.university.lab07.exceptions.CsvParseException;
import com.university.lab07.statistics.CsvStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Загрузка CSV двумя способами: классическим (BufferedReader) и через Stream API.
 * Оба метода дают одинаковый результат.
 */
public final class CsvLoader {

    private static final Logger logger = LoggerFactory.getLogger(CsvLoader.class);

    private CsvLoader() {
    }

    /**
     * Способ 1. Построчное чтение через BufferedReader: в памяти одновременно одна строка,
     * поэтому подход подходит для больших файлов.
     *
     * @throws IOException если файл не найден или не читается
     */
    public static <T> LoadResult<T> loadWithBufferedReader(Path path, CsvRecordMapper<T> mapper)
            throws IOException {
        logger.info("Чтение файла через BufferedReader: {}", path);
        CsvStatistics stats = new CsvStatistics();
        List<T> items = new ArrayList<>();
        Set<String> seenKeys = new HashSet<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            int lineNumber = header == null ? 0 : 1;

            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    T item = mapper.fromFields(CsvLineParser.parse(line));
                    addUnique(item, mapper, seenKeys, items, stats, lineNumber);
                } catch (CsvParseException e) {
                    stats.incrementError(e.getType());
                    logger.warn("Строка {} пропущена: {}", lineNumber, e.getMessage());
                }
            }
            stats.setTotalLines(lineNumber);
        }
        return new LoadResult<>(items, stats);
    }

    /**
     * Способ 2. Files.readAllLines() + Stream API: короче и декларативнее,
     * но весь файл сначала загружается в память.
     *
     * @throws IOException если файл не найден или не читается
     */
    public static <T> LoadResult<T> loadWithStreams(Path path, CsvRecordMapper<T> mapper)
            throws IOException {
        logger.info("Чтение файла через Files.readAllLines() и Stream API: {}", path);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        CsvStatistics stats = new CsvStatistics();
        stats.setTotalLines(lines.size());

        // Шаг 1: каждая строка превращается в результат разбора (объект или ошибка)
        List<ParseOutcome<T>> outcomes = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            outcomes.add(ParseOutcome.of(lines.get(i), i + 1, mapper));
        }

        // Шаг 2: ошибки пропускаем и считаем
        outcomes.stream()
                .filter(ParseOutcome::isError)
                .forEach(outcome -> {
                    stats.incrementError(outcome.error().getType());
                    logger.warn("Строка {} пропущена: {}", outcome.lineNumber(), outcome.error().getMessage());
                });

        // Шаг 3: из успешных строк убираем дубликаты
        List<ParseOutcome<T>> parsed = outcomes.stream()
                .filter(outcome -> !outcome.isError())
                .collect(Collectors.toList());

        Set<String> seenKeys = new HashSet<>();
        List<T> items = new ArrayList<>();
        parsed.forEach(outcome ->
                addUnique(outcome.item(), mapper, seenKeys, items, stats, outcome.lineNumber()));
        return new LoadResult<>(items, stats);
    }

    /** Добавляет объект в результат, если его ключ ещё не встречался; иначе считает дубликатом. */
    private static <T> void addUnique(T item, CsvRecordMapper<T> mapper, Set<String> seenKeys,
                                      List<T> items, CsvStatistics stats, int lineNumber) {
        String key = mapper.uniqueKey(item);
        if (key != null && !seenKeys.add(key)) {
            stats.incrementDuplicate();
            logger.warn("Дубликат ключа '{}' (строка {}) пропущен", key, lineNumber);
            return;
        }
        items.add(item);
        stats.incrementSuccess();
    }

    /** Результат разбора одной строки: либо объект, либо ошибка. */
    private record ParseOutcome<T>(T item, CsvParseException error, int lineNumber) {

        static <T> ParseOutcome<T> of(String line, int lineNumber, CsvRecordMapper<T> mapper) {
            try {
                return new ParseOutcome<>(mapper.fromFields(CsvLineParser.parse(line)), null, lineNumber);
            } catch (CsvParseException e) {
                return new ParseOutcome<>(null, e, lineNumber);
            }
        }

        boolean isError() {
            return error != null;
        }
    }
}
