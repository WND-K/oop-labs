package com.university.lab05.structure;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

/**
 * Обобщённая очередь (FIFO: первым пришёл — первым ушёл) на односвязных узлах.
 *
 * @param <T> тип элементов очереди
 */
public class Queue<T> {

    /** Узел списка: значение и ссылка на следующий узел. */
    private static class Node<T> {
        private final T value;
        private Node<T> next;

        private Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head; // начало очереди: отсюда забираем элементы
    private Node<T> tail; // конец очереди: сюда добавляем элементы
    private int size;

    /**
     * Добавляет элемент в конец очереди.
     *
     * @throws IllegalArgumentException если элемент равен null
     */
    public void enqueue(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Нельзя добавить null в очередь");
        }
        Node<T> node = new Node<>(item);
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    /**
     * Забирает элемент из начала очереди.
     *
     * @throws NoSuchElementException если очередь пуста
     */
    public T dequeue() {
        T value = peek();
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    /**
     * Возвращает элемент из начала очереди, не удаляя его.
     *
     * @throws NoSuchElementException если очередь пуста
     */
    public T peek() {
        if (head == null) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return head.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Node<T> node = head; node != null; node = node.next) {
            joiner.add(String.valueOf(node.value));
        }
        return joiner.toString();
    }
}
