package com.university.lab05.util;

import java.util.Objects;

/**
 * Обобщённые методы для работы с массивами.
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }

    /**
     * Меняет местами два элемента массива любого ссылочного типа.
     *
     * @param array массив
     * @param i     индекс первого элемента
     * @param j     индекс второго элемента
     * @param <T>   тип элементов массива
     * @throws NullPointerException           если массив равен null
     * @throws ArrayIndexOutOfBoundsException если индекс выходит за границы массива
     */
    public static <T> void swap(T[] array, int i, int j) {
        Objects.requireNonNull(array, "array");
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
