package com.university.lab06.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Чтение и запись файла журнала: примеры try-with-resources и try-catch-finally.
 */
public class LogFileService {

    private static final Logger logger = LoggerFactory.getLogger(LogFileService.class);

    /**
     * Дописывает строку в конец файла (try-with-resources: writer закроется сам).
     */
    public void appendLine(Path path, String line) throws IOException {
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(line);
            writer.newLine();
        }
    }

    /**
     * Читает последние строки файла. Здесь для сравнения используется классический
     * try-catch-finally: блок finally выполняется всегда.
     *
     * @param count сколько последних строк вернуть
     * @return список строк (пустой, если файл прочитать не удалось)
     */
    public List<String> readLastLines(Path path, int count) {
        BufferedReader reader = null;
        Deque<String> tail = new ArrayDeque<>();
        try {
            reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String line;
            while ((line = reader.readLine()) != null) {
                tail.addLast(line);
                if (tail.size() > count) {
                    tail.removeFirst();
                }
            }
        } catch (IOException e) {
            logger.warn("Не удалось прочитать файл {}: {}", path, e.getMessage());
            return new ArrayList<>();
        } finally {
            // выполняется и после успешного чтения, и после ошибки
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.warn("Не удалось закрыть файл {}", path);
                }
            }
            logger.debug("Чтение файла {} завершено", path);
        }
        return new ArrayList<>(tail);
    }
}
