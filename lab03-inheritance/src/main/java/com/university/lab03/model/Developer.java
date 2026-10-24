package com.university.lab03.model;

import com.university.lab03.util.Validation;

/**
 * Разработчик: бонус 10% плюс технологическая надбавка.
 */
public class Developer extends Employee implements Reportable {

    private static final double BONUS_RATE = 0.10;

    /** Заглушка: число завершённых задач для отчёта. */
    private static final int STUB_COMPLETED_TASKS = 10;

    private String programmingLanguage;
    private double techBonus;

    public Developer(String name, String department, double salary,
                     String programmingLanguage, double techBonus) {
        super(name, department, salary);
        setProgrammingLanguage(programmingLanguage);
        setTechBonus(techBonus);
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public final void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = Validation.requireNotBlank(programmingLanguage, "Язык программирования");
    }

    public double getTechBonus() {
        return techBonus;
    }

    public final void setTechBonus(double techBonus) {
        this.techBonus = Validation.requireNonNegative(techBonus, "Технологический бонус");
    }

    /** @return бонус разработчика: 10% от оклада + techBonus */
    @Override
    public double calculateBonus() {
        return getSalary() * BONUS_RATE + techBonus;
    }

    /** Отчёт: список завершённых задач (заглушка — фиксированное число). */
    @Override
    public String generateReport() {
        return "Отчёт разработчика " + getName() + ": " + STUB_COMPLETED_TASKS + " завершённых задач";
    }

    @Override
    public String toString() {
        return super.toString() + " — разработчик, язык: " + programmingLanguage;
    }
}
