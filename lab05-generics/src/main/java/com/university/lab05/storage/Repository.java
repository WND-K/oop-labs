package com.university.lab05.storage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Универсальное хранилище объектов типа T с автоматической выдачей идентификаторов.
 *
 * @param <T> тип хранимых объектов
 */
public class Repository<T> {

    /** LinkedHashMap хранит элементы в порядке добавления, поэтому вывод предсказуем. */
    private final Map<Long, T> storage = new LinkedHashMap<>();
    private long nextId = 1;

    /**
     * Сохраняет объект и возвращает выданный ему идентификатор.
     *
     * @throws NullPointerException если сущность равна null
     */
    public long save(T entity) {
        Objects.requireNonNull(entity, "entity");
        long id = nextId++;
        storage.put(id, entity);
        return id;
    }

    /** @return объект с указанным id или null, если такого нет */
    public T findById(long id) {
        return storage.get(id);
    }

    /** @return true, если объект был найден и удалён */
    public boolean delete(long id) {
        return storage.remove(id) != null;
    }

    /** @return копия списка всех объектов (изменение списка не влияет на хранилище) */
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public int size() {
        return storage.size();
    }
}
