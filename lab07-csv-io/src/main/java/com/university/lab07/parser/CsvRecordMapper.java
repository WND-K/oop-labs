package com.university.lab07.parser;

import com.university.lab07.exceptions.CsvParseException;

import java.util.List;

/**
 * Описание того, как строка CSV превращается в объект типа T и обратно.
 * Благодаря этому интерфейсу один и тот же загрузчик работает и с книгами, и с сотрудниками.
 *
 * @param <T> тип создаваемых объектов
 */
public interface CsvRecordMapper<T> {

    /** @return строка заголовка CSV-файла */
    String header();

    /**
     * Создаёт объект из полей строки.
     *
     * @throws CsvParseException если строка некорректна
     */
    T fromFields(List<String> fields) throws CsvParseException;

    /** @return значения полей объекта для записи в CSV */
    List<String> toFields(T item);

    /**
     * @return ключ для поиска дубликатов или null, если дубликаты не проверяются
     */
    default String uniqueKey(T item) {
        return null;
    }
}
