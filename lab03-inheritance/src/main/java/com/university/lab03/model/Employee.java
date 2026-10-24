package com.university.lab03.model;

import com.university.lab03.util.Validation;

/**
 * Базовый класс иерархии «Сотрудники».
 */
public class Employee {

    /** Доля бонуса обычного сотрудника: 5% от оклада. */
    private static final double BONUS_RATE = 0.05;

    private String name;
    private String department;
    private double salary;

    /**
     * @param name       имя (не пустое)
     * @param department отдел (не пустой)
     * @param salary     оклад (больше 0)
     */
    public Employee(String name, String department, double salary) {
        setName(name);
        setDepartment(department);
        setSalary(salary);
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = Validation.requireNotBlank(name, "Имя");
    }

    public String getDepartment() {
        return department;
    }

    public final void setDepartment(String department) {
        this.department = Validation.requireNotBlank(department, "Отдел");
    }

    public double getSalary() {
        return salary;
    }

    public final void setSalary(double salary) {
        this.salary = Validation.requirePositive(salary, "Оклад");
    }

    /** @return бонус: 5% от оклада */
    public double calculateBonus() {
        return salary * BONUS_RATE;
    }

    @Override
    public String toString() {
        return name + " (" + department + ")";
    }
}
