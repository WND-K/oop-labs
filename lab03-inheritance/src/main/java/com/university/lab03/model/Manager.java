package com.university.lab03.model;

import com.university.lab03.util.Validation;

/**
 * Менеджер: бонус 15%, есть подчинённые.
 */
public class Manager extends Employee implements Reportable {

    private static final double BONUS_RATE = 0.15;

    private int subordinatesCount;

    public Manager(String name, String department, double salary, int subordinatesCount) {
        super(name, department, salary); // вызов конструктора базового класса
        setSubordinatesCount(subordinatesCount);
    }

    public int getSubordinatesCount() {
        return subordinatesCount;
    }

    public final void setSubordinatesCount(int subordinatesCount) {
        this.subordinatesCount = (int) Validation.requireNonNegative(subordinatesCount, "Число подчинённых");
    }

    /** @return бонус менеджера: 15% от оклада */
    @Override
    public double calculateBonus() {
        return getSalary() * BONUS_RATE;
    }

    /** Отчёт: список подчинённых (заглушка — выводится только количество). */
    @Override
    public String generateReport() {
        return "Отчёт менеджера " + getName() + ": " + subordinatesCount + " подчинённых";
    }

    @Override
    public String toString() {
        return super.toString() + " — менеджер, подчинённых: " + subordinatesCount;
    }
}
