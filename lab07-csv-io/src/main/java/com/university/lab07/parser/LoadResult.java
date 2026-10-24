package com.university.lab07.parser;

import com.university.lab07.statistics.CsvStatistics;

import java.util.List;

/**
 * Результат загрузки: прочитанные объекты и статистика.
 */
public record LoadResult<T>(List<T> items, CsvStatistics statistics) {
}
