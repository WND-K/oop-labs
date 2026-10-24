package com.university.lab07.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Разбиение строки CSV на поля с учётом кавычек (RFC 4180).
 * <ul>
 *   <li>запятая внутри кавычек не является разделителем;</li>
 *   <li>две кавычки подряд внутри кавычек означают одну кавычку.</li>
 * </ul>
 * Ограничение: поле не может содержать перевод строки, так как файл читается построчно.
 */
public final class CsvLineParser {

    private static final char DELIMITER = ',';
    private static final char QUOTE = '"';

    private CsvLineParser() {
    }

    /**
     * @param line строка CSV
     * @return список значений полей (пустое поле даёт пустую строку)
     */
    public static List<String> parse(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == QUOTE) {
                    if (i + 1 < line.length() && line.charAt(i + 1) == QUOTE) {
                        field.append(QUOTE); // экранированная кавычка
                        i++;
                    } else {
                        inQuotes = false;    // закрывающая кавычка
                    }
                } else {
                    field.append(c);
                }
            } else if (c == QUOTE) {
                inQuotes = true;
            } else if (c == DELIMITER) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }
        fields.add(field.toString());
        return fields;
    }
}
