package com.university.lab04.service;

import com.university.lab04.model.Employee;
import com.university.lab04.util.CollectionBenchmark;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Индивидуальное задание (вариант 2): коллекции и Stream API на примере Employee.
 */
public class EmployeeDemo {

    private static final double SALARY_THRESHOLD = 100_000;
    private static final int BENCHMARK_SIZE = 100_000;
    private static final int BENCHMARK_DEPARTMENTS = 50;
    private static final double BENCHMARK_BASE_SALARY = 30_000;

    public void run() {
        List<Employee> employees = createEmployees();
        demoSorting(employees);
        demoSet(employees);
        demoGrouping(employees);
        demoStreams(employees);
        demoBenchmark();
    }

    private List<Employee> createEmployees() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee(1, "Иванов И.И.", "инженер", "Отдел разработки", 120000));
        list.add(new Employee(2, "Петрова А.С.", "аналитик", "Отдел данных", 95000));
        list.add(new Employee(3, "Сидоров П.П.", "тестировщик", "Отдел качества", 80000));
        list.add(new Employee(4, "Козлова М.В.", "руководитель", "Отдел разработки", 180000));
        list.add(new Employee(5, "Смирнов Д.А.", "инженер", "Отдел разработки", 110000));
        list.add(new Employee(6, "Орлова Е.К.", "аналитик", "Отдел данных", 105000));
        list.add(new Employee(7, "Фёдоров Н.Н.", "тестировщик", "Отдел качества", 85000));
        list.add(new Employee(8, "Белова Т.Р.", "руководитель", "Отдел данных", 150000));
        list.add(new Employee(9, "Громов С.В.", "стажёр", "Отдел качества", 45000));
        list.add(new Employee(10, "Дмитриев А.А.", "стажёр", "Отдел разработки", 50000));
        return list;
    }

    private void demoSorting(List<Employee> employees) {
        System.out.println("=== Сотрудники: три вида сортировки ===");

        List<Employee> byName = new ArrayList<>(employees);
        byName.sort(Comparator.comparing(Employee::getFullName));
        System.out.println("По ФИО:");
        byName.forEach(e -> System.out.println("  " + e));

        List<Employee> byPosition = new ArrayList<>(employees);
        byPosition.sort(Comparator.comparing(Employee::getPosition)
                .thenComparing(Employee::getFullName));
        System.out.println("По должности (затем по ФИО):");
        byPosition.forEach(e -> System.out.println("  " + e));

        List<Employee> bySalary = new ArrayList<>(employees);
        bySalary.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
        System.out.println("По зарплате (по убыванию):");
        bySalary.forEach(e -> System.out.println("  " + e));
    }

    private void demoSet(List<Employee> employees) {
        System.out.println();
        System.out.println("=== Сотрудники: Set и дубликаты ===");
        Set<Employee> unique = new HashSet<>(employees);
        System.out.println("Уникальных сотрудников: " + unique.size());

        Employee duplicate = new Employee(99, "Иванов И.И.", "инженер", "Отдел разработки", 120000);
        System.out.println("Дубликат добавлен: " + unique.add(duplicate));
        System.out.println("Размер множества после попытки: " + unique.size());
    }

    private void demoGrouping(List<Employee> employees) {
        System.out.println();
        System.out.println("=== Сотрудники: группировка по отделу ===");
        Map<String, List<Employee>> byDepartment = new HashMap<>();
        for (Employee e : employees) {
            byDepartment.computeIfAbsent(e.getDepartment(), k -> new ArrayList<>()).add(e);
        }
        new TreeMap<>(byDepartment).forEach((dept, list) ->
                System.out.println(dept + ": " + list.size() + " чел."));
    }

    private void demoStreams(List<Employee> employees) {
        System.out.println();
        System.out.println("=== Сотрудники: Stream API ===");

        List<String> wellPaid = employees.stream()
                .filter(e -> e.getSalary() > SALARY_THRESHOLD)
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .map(Employee::getFullName)
                .collect(Collectors.toList());
        System.out.println("Зарплата > " + (int) SALARY_THRESHOLD + ": " + wellPaid);

        Map<String, Double> avgByDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, TreeMap::new,
                        Collectors.averagingDouble(Employee::getSalary)));
        System.out.println("Средняя зарплата по отделам:");
        avgByDepartment.forEach((dept, avg) ->
                System.out.println(String.format(Locale.US, "  %s: %.2f руб.", dept, avg)));
    }

    private void demoBenchmark() {
        System.out.println();
        System.out.println("=== Сотрудники: ArrayList и HashSet ===");
        CollectionBenchmark.compareListAndSet(BENCHMARK_SIZE, i -> new Employee(i,
                "Сотрудник " + i,
                "должность",
                "Отдел " + (i % BENCHMARK_DEPARTMENTS),
                BENCHMARK_BASE_SALARY + i));
    }
}
