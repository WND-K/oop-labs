package com.university.lab06.repository;

import com.university.lab06.exceptions.BookNotFoundException;
import com.university.lab06.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Хранилище книг в памяти с логированием операций.
 */
public class BookRepository {

    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);

    private final Map<Long, Book> storage = new LinkedHashMap<>();
    private long nextId = 1;

    /** Сохраняет книгу, присваивает ей id и пишет INFO в лог. */
    public Book save(Book book) {
        logger.info("Сохранение книги: {}", book.getTitle());
        book.setId(nextId++);
        storage.put(book.getId(), book);
        logger.info("Книга успешно сохранена: id={}, title={}", book.getId(), book.getTitle());
        return book;
    }

    /**
     * @throws BookNotFoundException если книги с таким id нет (checked-исключение)
     */
    public Book findById(long id) throws BookNotFoundException {
        Book book = storage.get(id);
        if (book == null) {
            logger.warn("Попытка получить книгу с id {}", id);
            throw new BookNotFoundException("Книга с id " + id + " не найдена");
        }
        return book;
    }

    /**
     * @throws BookNotFoundException если книги с таким id нет
     */
    public void delete(long id) throws BookNotFoundException {
        Book removed = storage.remove(id);
        if (removed == null) {
            logger.warn("Попытка удалить книгу с id {}", id);
            throw new BookNotFoundException("Книга с id " + id + " не найдена");
        }
        logger.info("Книга успешно удалена: id={}, title={}", id, removed.getTitle());
    }

    /** @return копия карты всех книг */
    public Map<Long, Book> findAll() {
        logger.debug("Получение всех книг. Количество: {}", storage.size());
        return new LinkedHashMap<>(storage);
    }
}
