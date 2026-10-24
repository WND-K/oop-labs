package com.university.lab03;

import com.university.lab03.model.Developer;
import com.university.lab03.model.Employee;
import com.university.lab03.model.Manager;
import com.university.lab03.model.Reportable;
import com.university.lab03.vehicle.Car;
import com.university.lab03.vehicle.Informative;
import com.university.lab03.vehicle.Motorcycle;
import com.university.lab03.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Точка входа: полиморфизм и работа с интерфейсными типами.
 */
public class Main {

    public static void main(String[] args) {
        // ===== Основное задание: иерархия «Сотрудники» =====
        Manager ivanov = new Manager("Иванов", "IT", 150000, 5);
        Developer petrov = new Developer("Петров", "IT", 120000, "Java", 3000);

        // Массив базового типа, внутри — объекты разных типов
        Employee[] employees = {
                ivanov,
                petrov,
                new Employee("Сидорова", "Бухгалтерия", 90000),
                new Manager("Козлов", "Продажи", 130000, 8),
                new Developer("Смирнова", "IT", 110000, "Python", 5000)
        };

        System.out.println("=== Полиморфизм ===");
        for (Employee e : employees) {
            // calculateBonus() и toString() выбираются по фактическому типу объекта
            System.out.printf(java.util.Locale.US, "%s — бонус: %.2f руб.%n", e, e.calculateBonus());
        }

        System.out.println();
        System.out.println("=== Интерфейс Reportable ===");
        List<Reportable> reporters = new ArrayList<>();
        reporters.add(ivanov);
        reporters.add(petrov);
        reporters.add(new Manager("Козлов", "Продажи", 130000, 8));
        for (Reportable r : reporters) {
            System.out.println(r.generateReport());
        }

        // ===== Индивидуальное задание, вариант 2: «Транспортные средства» =====
        Vehicle[] vehicles = {
                new Vehicle("Lada", "Largus", 160),
                new Car("Toyota", "Camry", 210, "седан"),
                new Motorcycle("Yamaha", "MT-07", 220, "двухцилиндровый"),
                new Car("Skoda", "Octavia", 200, "лифтбек")
        };

        System.out.println();
        System.out.println("=== Транспорт: расход топлива ===");
        for (Vehicle v : vehicles) {
            System.out.printf(java.util.Locale.US, "%s — расход: %.2f л/100 км%n", v, v.getFuelConsumption());
        }

        System.out.println();
        System.out.println("=== Интерфейс Informative ===");
        List<Informative> informatives = new ArrayList<>();
        for (Vehicle v : vehicles) {
            // instanceof: не каждый Vehicle реализует Informative
            if (v instanceof Informative info) {
                informatives.add(info);
            }
        }
        for (Informative info : informatives) {
            System.out.println(info.getInfo());
        }

        // ===== Проверка валидации =====
        System.out.println();
        System.out.println("=== Проверка валидации ===");
        try {
            new Manager("", "IT", 100000, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Сотрудник: " + e.getMessage());
        }
        try {
            new Car("Toyota", "Camry", -10, "седан");
        } catch (IllegalArgumentException e) {
            System.out.println("Транспорт: " + e.getMessage());
        }
    }
}
