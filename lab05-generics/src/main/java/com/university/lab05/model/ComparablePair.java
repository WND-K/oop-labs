package com.university.lab05.model;

import java.util.Objects;

/**
 * Пара значений одного типа, допускающая только сравнимые типы.
 * Ограничитель {@code T extends Comparable<T>} гарантирует, что у значений есть compareTo().
 * <p>
 * Оба элемента имеют один тип T, потому что сравнивать можно только значения одного типа:
 * в варианте Pair&lt;A, B&gt; для min() и max() пришлось бы сравнивать A с B.
 *
 * @param <T> тип элементов, реализующий Comparable
 */
public class ComparablePair<T extends Comparable<T>> {

    private final T first;
    private final T second;

    /**
     * @throws NullPointerException если один из элементов равен null
     */
    public ComparablePair(T first, T second) {
        this.first = Objects.requireNonNull(first, "first");
        this.second = Objects.requireNonNull(second, "second");
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    /** @return меньший из двух элементов (при равенстве — первый) */
    public T min() {
        return first.compareTo(second) <= 0 ? first : second;
    }

    /** @return больший из двух элементов (при равенстве — первый) */
    public T max() {
        return first.compareTo(second) >= 0 ? first : second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
