package com.university.lab07.parser;

import com.university.lab07.exceptions.CsvParseException;
import com.university.lab07.exceptions.ErrorType;
import com.university.lab07.model.Book;

import java.time.Year;
import java.util.List;

/**
 * Правила разбора и записи книг: id,title,author,year,isbn,publisher.
 */
public class BookMapper implements CsvRecordMapper<Book> {

    private static final int FIELDS_COUNT = 6;
    private static final int MIN_YEAR = 1;

    @Override
    public String header() {
        return "id,title,author,year,isbn,publisher";
    }

    @Override
    public Book fromFields(List<String> fields) throws CsvParseException {
        if (fields.size() < FIELDS_COUNT) {
            throw new CsvParseException(ErrorType.NOT_ENOUGH_FIELDS,
                    "Недостаточно полей: ожидается " + FIELDS_COUNT + ", получено " + fields.size());
        }
        if (fields.size() > FIELDS_COUNT) {
            throw new CsvParseException(ErrorType.TOO_MANY_FIELDS,
                    "Слишком много полей: ожидается " + FIELDS_COUNT + ", получено " + fields.size());
        }

        long id = parseId(fields.get(0));
        String title = fields.get(1).trim();
        String author = fields.get(2).trim();
        int year = parseYear(fields.get(3));
        String isbn = fields.get(4).trim();
        String publisher = fields.get(5).trim();

        if (title.isEmpty()) {
            throw new CsvParseException(ErrorType.EMPTY_TITLE, "Пустое название");
        }
        if (isbn.isEmpty()) {
            throw new CsvParseException(ErrorType.EMPTY_ISBN, "Пустой ISBN");
        }
        return new Book(id, title, author, year, isbn, publisher);
    }

    private long parseId(String value) throws CsvParseException {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new CsvParseException(ErrorType.NON_NUMERIC_ID, "Нечисловой id: '" + value + "'", e);
        }
    }

    private int parseYear(String value) throws CsvParseException {
        int year;
        try {
            year = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new CsvParseException(ErrorType.NON_NUMERIC_YEAR, "Нечисловой год: '" + value + "'", e);
        }
        int currentYear = Year.now().getValue();
        if (year < MIN_YEAR || year > currentYear) {
            throw new CsvParseException(ErrorType.INVALID_YEAR_RANGE,
                    "Год вне диапазона [" + MIN_YEAR + ", " + currentYear + "]: " + year);
        }
        return year;
    }

    @Override
    public List<String> toFields(Book book) {
        return List.of(String.valueOf(book.id()), book.title(), book.author(),
                String.valueOf(book.year()), book.isbn(), book.publisher());
    }

    /** Дубликатом считается книга с уже встречавшимся ISBN. */
    @Override
    public String uniqueKey(Book book) {
        return book.isbn();
    }
}
