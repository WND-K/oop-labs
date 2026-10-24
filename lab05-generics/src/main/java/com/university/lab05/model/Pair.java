package com.university.lab05.model;

/**
 * Обобщённая пара из двух значений произвольных (и необязательно одинаковых) типов.
 *
 * @param <A> тип первого элемента
 * @param <B> тип второго элемента
 */
public class Pair<A, B> {

    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
