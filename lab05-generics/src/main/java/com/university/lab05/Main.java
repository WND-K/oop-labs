package com.university.lab05;

import com.university.lab05.model.Book;
import com.university.lab05.model.ComparablePair;
import com.university.lab05.model.Pair;
import com.university.lab05.storage.Repository;
import com.university.lab05.structure.Queue;
import com.university.lab05.util.ArrayUtils;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Точка входа: демонстрация обобщённых классов и методов.
 */
public class Main {

    public static void main(String[] args) {
        demoPair();
        demoSwap();
        demoComparablePair();
        demoRepository();
        demoQueue();
    }

    private static void demoPair() {
        System.out.println("=== Pair<A, B> ===");
        Pair<String, Integer> nameAndAge = new Pair<>("Анна", 20);
        Pair<Double, Boolean> other = new Pair<>(3.14, true);
        System.out.println(nameAndAge + ": " + nameAndAge.getFirst() + ", " + nameAndAge.getSecond());
        System.out.println(other);
    }

    private static void demoSwap() {
        System.out.println();
        System.out.println("=== Обобщённый метод swap ===");
        Integer[] numbers = {1, 2, 3, 4};
        String[] words = {"раз", "два", "три"};
        ArrayUtils.swap(numbers, 0, 3);
        ArrayUtils.swap(words, 0, 2);
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(words));
    }

    private static void demoComparablePair() {
        System.out.println();
        System.out.println("=== ComparablePair<T extends Comparable<T>> ===");
        ComparablePair<Integer> ints = new ComparablePair<>(7, 3);
        ComparablePair<String> strings = new ComparablePair<>("яблоко", "арбуз");
        System.out.println(ints + ": min = " + ints.min() + ", max = " + ints.max());
        System.out.println(strings + ": min = " + strings.min() + ", max = " + strings.max());
    }

    private static void demoRepository() {
        System.out.println();
        System.out.println("=== Repository<T> ===");
        Repository<Book> repository = new Repository<>();
        long id1 = repository.save(new Book("Война и мир", 1869));
        long id2 = repository.save(new Book("Мастер и Маргарита", 1967));
        long id3 = repository.save(new Book("Идиот", 1869));
        System.out.println("Выданные id: " + id1 + ", " + id2 + ", " + id3);
        System.out.println("findById(2): " + repository.findById(id2));
        System.out.println("Удаление id=2: " + repository.delete(id2));
        System.out.println("Повторное удаление id=2: " + repository.delete(id2));
        System.out.println("findById(2) после удаления: " + repository.findById(id2));
        System.out.println("findAll(): " + repository.findAll());

        Repository<String> names = new Repository<>();
        names.save("Иванов");
        System.out.println("Хранилище строк: " + names.findAll());
    }

    private static void demoQueue() {
        System.out.println();
        System.out.println("=== Индивидуальное задание, вариант 2: Queue<T> ===");
        Queue<Integer> numbers = new Queue<>();
        System.out.println("Пуста ли очередь: " + numbers.isEmpty());
        numbers.enqueue(10);
        numbers.enqueue(20);
        numbers.enqueue(30);
        System.out.println("После enqueue: " + numbers + ", размер " + numbers.size());
        System.out.println("peek(): " + numbers.peek() + " (элемент остался в очереди)");
        System.out.println("dequeue(): " + numbers.dequeue());
        System.out.println("dequeue(): " + numbers.dequeue());
        System.out.println("Осталось: " + numbers + ", размер " + numbers.size());
        numbers.dequeue();
        System.out.println("Пуста ли очередь: " + numbers.isEmpty());

        Queue<String> words = new Queue<>();
        words.enqueue("первый");
        words.enqueue("второй");
        System.out.println("Очередь строк: " + words + ", извлечён: " + words.dequeue());

        // Стирание типов: во время выполнения Queue<Integer> и Queue<String> — один и тот же класс
        System.out.println("Один класс у Queue<Integer> и Queue<String>: "
                + (numbers.getClass() == words.getClass()));

        System.out.println();
        System.out.println("=== Проверка ошибок ===");
        try {
            numbers.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println("dequeue() из пустой очереди: " + e.getMessage());
        }
        try {
            words.enqueue(null);
        } catch (IllegalArgumentException e) {
            System.out.println("enqueue(null): " + e.getMessage());
        }
    }
}
