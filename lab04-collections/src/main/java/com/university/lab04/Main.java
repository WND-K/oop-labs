package com.university.lab04;

import com.university.lab04.service.BookDemo;
import com.university.lab04.service.EmployeeDemo;

/**
 * Точка входа: запускает демонстрации основного и индивидуального заданий.
 */
public class Main {

    public static void main(String[] args) {
        new BookDemo().run();
        System.out.println();
        System.out.println("##################################################");
        System.out.println();
        new EmployeeDemo().run();
    }
}
