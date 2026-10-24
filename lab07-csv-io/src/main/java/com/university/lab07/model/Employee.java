package com.university.lab07.model;

import java.time.LocalDate;

/**
 * Сотрудник (индивидуальное задание, вариант 2).
 */
public record Employee(long id, String fullName, String position, double salary,
                       String department, LocalDate hireDate) {
}
