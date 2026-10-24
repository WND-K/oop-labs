package com.university.lab06.model;

import com.university.lab06.exceptions.InvalidEmployeeDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Сотрудник (индивидуальное задание, вариант 2).
 * Валидация в сеттерах: fullName, position и department не пустые, salary больше нуля.
 */
public class Employee {

    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

    private long id;
    private String fullName;
    private String position;
    private double salary;
    private String department;

    /**
     * @throws InvalidEmployeeDataException если одно из значений некорректно
     */
    public Employee(String fullName, String position, double salary, String department) {
        setFullName(fullName);
        setPosition(position);
        setSalary(salary);
        setDepartment(department);
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

    public final void setFullName(String fullName) {
        this.fullName = requireNotBlank(fullName, "fullName");
    }

    public String getPosition() {
        return position;
    }

    public final void setPosition(String position) {
        this.position = requireNotBlank(position, "position");
    }

    public double getSalary() {
        return salary;
    }

    public final void setSalary(double salary) {
        if (salary <= 0) {
            logger.error("Ошибка валидации: salary должно быть > 0, получено {}", salary);
            throw new InvalidEmployeeDataException("salary должно быть > 0");
        }
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public final void setDepartment(String department) {
        this.department = requireNotBlank(department, "department");
    }

    /** Общая проверка «строка не null и не пустая» для текстовых полей. */
    private static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            logger.error("Ошибка валидации: {} не может быть null или пустым", fieldName);
            throw new InvalidEmployeeDataException(fieldName + " не может быть null или пустым");
        }
        return value;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Employee{id=%d, fullName='%s', position='%s', salary=%.2f, department='%s'}",
                id, fullName, position, salary, department);
    }
}
