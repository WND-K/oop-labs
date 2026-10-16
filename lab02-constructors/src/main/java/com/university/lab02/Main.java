package com.university.lab02;

import com.university.lab02.model.Book;
import com.university.lab02.model.Employee;

import java.util.Locale;

/**
 * Точка входа: демонстрация конструкторов, статического счётчика,
 * фабричных методов и перегруженных методов.
 */
public class Main {

    public static void main(String[] args) {
        // Основное задание: книги
        System.out.println("=== Создание книг ===");
        Book book1 = new Book(1, "Война и мир", "Толстой Л.Н.", 1869);
        System.out.println("Создано через конструктор: " + book1.getDescription());

        Book book2 = Book.createBook("Мастер и Маргарита", "Булгаков М.А.", 1967);
        System.out.println("Создано через фабричный метод: " + book2.getDescription());

        Book book3 = new Book("Преступление и наказание", "Достоевский Ф.М.", 1866);
        System.out.println("Краткое описание: " + book3.getDescription(true));

        System.out.println();
        System.out.println("=== Статистика ===");
        System.out.println("Всего создано книг: " + Book.getCounter());

        // Остальные конструкторы
        System.out.println();
        System.out.println("=== Другие конструкторы Book ===");
        Book emptyBook = new Book();
        Book titleAuthorBook = new Book("Евгений Онегин", "Пушкин А.С.");
        System.out.println("Без параметров: " + emptyBook);
        System.out.println("Название и автор: " + titleAuthorBook);
        System.out.println("Всего создано книг: " + Book.getCounter());

        // Индивидуальное задание (вариант 2): сотрудники
        System.out.println();
        System.out.println("=== Создание сотрудников ===");
        Employee emp1 = new Employee(1, "Иванов И.И.", "инженер", "Отдел разработки", 120000);
        Employee emp2 = Employee.createEmployee("Петрова А.С.", "аналитик", "Отдел данных", 95000.5);
        Employee emp3 = new Employee("Сидоров П.П.", "тестировщик", 80000);
        System.out.println("Через конструктор: " + emp1);
        System.out.println("Через фабричный метод (id=" + emp2.getId() + "): " + emp2);
        System.out.println("Без отдела: " + emp3);
        System.out.println("Без оклада: " + emp1.getDescription(false));

        System.out.println();
        System.out.println("=== Годовой доход ===");
        System.out.printf(Locale.US, "Без премии: %.2f руб.%n", emp1.calculateAnnualSalary());
        System.out.printf(Locale.US, "С премией 10%%: %.2f руб.%n", emp1.calculateAnnualSalary(10));

        System.out.println();
        System.out.println("=== Статистика ===");
        System.out.println("Всего создано сотрудников: " + Employee.getCounter());

        // Проверка: при ошибке валидации счётчик не должен увеличиваться
        System.out.println();
        System.out.println("=== Проверка валидации ===");
        int before = Book.getCounter();
        try {
            new Book("", "Автор", 2000);
        } catch (IllegalArgumentException e) {
            System.out.println("Книга: " + e.getMessage());
        }
        System.out.println("Счётчик до и после ошибки: " + before + " и " + Book.getCounter());
        try {
            new Employee("Козлов К.К.", "менеджер", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Сотрудник: " + e.getMessage());
        }
    }
}