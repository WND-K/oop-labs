package com.university.lab07.io;

import com.university.lab07.parser.CsvRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Запись объектов в CSV-файл с заголовком и экранированием.
 */
public final class CsvWriter {

    private static final Logger logger = LoggerFactory.getLogger(CsvWriter.class);

    private CsvWriter() {
    }

    /**
     * @throws IOException при ошибке записи
     */
    public static <T> void write(Path path, CsvRecordMapper<T> mapper, List<T> items) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(mapper.header());
            writer.newLine();
            for (T item : items) {
                writer.write(mapper.toFields(item).stream()
                        .map(CsvWriter::escape)
                        .collect(Collectors.joining(",")));
                writer.newLine();
            }
        }
        logger.info("Записано {} записей в файл {}", items.size(), path);
    }

    /**
     * Если значение содержит запятую, кавычку или пробелы по краям, оно берётся в кавычки,
     * а кавычки внутри удваиваются.
     */
    static String escape(String value) {
        if (value == null) {
            return "";
        }
        boolean needQuotes = value.contains(",") || value.contains("\"") || value.contains("\n")
                || !value.equals(value.trim());
        if (!needQuotes) {
            return value;
        }
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
