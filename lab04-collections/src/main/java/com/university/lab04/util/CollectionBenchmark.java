package com.university.lab04.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.IntFunction;

/**
 * Замер времени поиска (contains) в разных коллекциях.
 */
public final class CollectionBenchmark {

    /** Сколько раз повторяется поиск в каждой коллекции. */
    private static final int SEARCHES = 1000;

    private CollectionBenchmark() {
    }

    /**
     * Сравнивает поиск в ArrayList и HashSet из одинаковых элементов.
     *
     * @param size    количество элементов в коллекциях
     * @param factory создаёт элемент по номеру
     */
    public static <T> void compareListAndSet(int size, IntFunction<T> factory) {
        List<T> list = new ArrayList<>(size);
        Set<T> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            T item = factory.apply(i);
            list.add(item);
            set.add(item);
        }
        // Искомые элементы равномерно распределены по списку
        List<T> targets = new ArrayList<>(SEARCHES);
        int step = Math.max(1, size / SEARCHES);
        for (int i = 0; i < SEARCHES; i++) {
            targets.add(factory.apply((i * step) % size));
        }

        measure(list, targets); // «прогрев» JVM, результат не выводится
        measure(set, targets);

        double listMs = measure(list, targets);
        double setMs = measure(set, targets);
        System.out.printf(Locale.US, "Элементов: %d, поисков: %d%n", size, SEARCHES);
        System.out.printf(Locale.US, "ArrayList: %.3f мс (%.3f мкс на поиск)%n",
                listMs, listMs * 1000 / SEARCHES);
        System.out.printf(Locale.US, "HashSet:   %.3f мс (%.3f мкс на поиск)%n",
                setMs, setMs * 1000 / SEARCHES);
    }

    /** @return время выполнения всех поисков в миллисекундах */
    private static <T> double measure(Collection<T> collection, List<T> targets) {
        long start = System.nanoTime();
        int found = 0;
        for (T target : targets) {
            if (collection.contains(target)) {
                found++;
            }
        }
        long elapsed = System.nanoTime() - start;
        if (found != targets.size()) {
            throw new IllegalStateException("Найдены не все элементы: " + found);
        }
        return elapsed / 1_000_000.0;
    }
}
