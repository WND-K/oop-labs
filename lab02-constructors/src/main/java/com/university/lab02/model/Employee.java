package com.university.lab02.model;

import java.util.Locale;

/**
 * Сотрудник (индивидуальное задание, вариант 2).
 * Лабораторная работа №2: перегруженные конструкторы, статический счётчик,
 * фабричный метод и перегруженные методы.
 */
public class Employee {

    private static final int MONTHS_IN_YEAR = 12;
    private static final String DEFAULT_DEPARTMENT = "Не указан";

    /** Счётчик созданных сотрудников. */
    private static int employeeCounter = 0;

    private long id;
    private String fullName;
    private String position;
    private String department;
    private double salary;

    /**
     * Основной конструктор; остальные вызывают его через this(...).
     *
     * @param id         идентификатор
     * @param fullName   ФИО (не пусто)
     * @param position   должность (не пусто)
     * @param department отдел (не пусто)
     * @param salary     оклад (строго больше 0)
     */
    public Employee(long id, String fullName, String position, String department, double salary) {
        this.id = id;
        setFullName(fullName);
        setPosition(position);
        setDepartment(department);
        setSalary(salary);
        employeeCounter++; // только после успешных проверок
    }

    /** Конструктор без id (id=0). */
    public Employee(String fullName, String position, String department, double salary) {
        this(0, fullName, position, department, salary);
    }

    /** Конструктор без id и отдела (отдел «Не указан»). */
    public Employee(String fullName, String position, double salary) {
        this(fullName, position, DEFAULT_DEPARTMENT, salary);
    }

    /**
     * Статический фабричный метод с автоинкрементным id
     * (количество созданных сотрудников + 1).
     */
    public static Employee createEmployee(String fullName, String position,
                                          String department, double salary) {
        return new Employee(employeeCounter + 1L, fullName, position, department, salary);
    }

    /** @return количество созданных сотрудников */
    public static int getCounter() {
        return employeeCounter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = requireNotBlank(fullName, "ФИО");
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = requireNotBlank(position, "Должность");
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = requireNotBlank(department, "Отдел");
    }

    public double getSalary() {
        return salary;
    }

    /** @throws IllegalArgumentException если оклад не больше нуля */
    public void setSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Оклад должен быть больше 0: " + salary);
        }
        this.salary = salary;
    }

    /**
     * Годовой доход без премии: оклад × 12.
     */
    public double calculateAnnualSalary() {
        return salary * MONTHS_IN_YEAR;
    }

    /**
     * Перегруженный вариант: годовой доход с премией в процентах от годового оклада.
     *
     * @param bonusPercent премия в процентах (не меньше 0)
     * @throws IllegalArgumentException если премия отрицательная
     */
    public double calculateAnnualSalary(double bonusPercent) {
        if (bonusPercent < 0) {
            throw new IllegalArgumentException("Премия не может быть отрицательной: " + bonusPercent);
        }
        return calculateAnnualSalary() * (1 + bonusPercent / 100);
    }

    /**
     * @return Иванов И.И. — инженер (Отдел разработки), оклад 120000.00 руб.
     */
    public String getDescription() {
        return getDescription(true);
    }

    /**
     * Перегруженный вариант описания.
     *
     * @param withSalary true — с окладом, false — без него
     */
    public String getDescription(boolean withSalary) {
        String base = String.format("%s — %s (%s)", fullName, position, department);
        if (withSalary) {
            return base + String.format(Locale.US, ", оклад %.2f руб.", salary);
        }
        return base;
    }

    @Override
    public String toString() {
        return getDescription();
    }

    /** Общая проверка «строка не null и не пустая». */
    private static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
        return value;
    }
}