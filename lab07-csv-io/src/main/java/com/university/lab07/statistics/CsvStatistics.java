package com.university.lab07.statistics;

import com.university.lab07.exceptions.ErrorType;

import java.util.EnumMap;
import java.util.Map;

/**
 * Статистика обработки CSV-файла.
 */
public class CsvStatistics {

    private int totalLines;
    private int successCount;
    private int duplicateCount;
    private int errorCount;
    /** EnumMap хранит типы ошибок в порядке объявления enum, поэтому вывод стабилен. */
    private final Map<ErrorType, Integer> errorTypes = new EnumMap<>(ErrorType.class);

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public void incrementSuccess() {
        successCount++;
    }

    public void incrementDuplicate() {
        duplicateCount++;
    }

    public void incrementError(ErrorType type) {
        errorCount++;
        errorTypes.merge(type, 1, Integer::sum);
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getDuplicateCount() {
        return duplicateCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    /**
     * @param duplicateLabel подпись строки про дубликаты (например, «Дубликатов по ISBN»);
     *                       null — строка про дубликаты не выводится
     */
    public String format(String duplicateLabel) {
        StringBuilder sb = new StringBuilder();
        sb.append("Статистика:\n");
        sb.append("- Всего строк в файле: ").append(totalLines).append(" (включая заголовок)\n");
        sb.append("- Успешно прочитано: ").append(successCount).append("\n");
        sb.append("- Пропущено с ошибками: ").append(errorCount).append("\n");
        errorTypes.forEach((type, count) ->
                sb.append("  - ").append(type.getLabel()).append(": ").append(count).append("\n"));
        if (duplicateLabel != null) {
            sb.append("- ").append(duplicateLabel).append(": ").append(duplicateCount).append("\n");
        }
        return sb.toString();
    }
}
