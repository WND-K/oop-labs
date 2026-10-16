package com.university.lab04.model;

import com.university.lab04.util.Validation;

import java.util.Locale;
import java.util.Objects;

/**
 * Сотрудник (индивидуальное задание, вариант 2).
 */
public class Employee {

    private long id;
    private String fullName;
    private String position;
    private String department;
    private double salary;

    /**
     * @param id         идентификатор
     * @param fullName   ФИО (не пусто)
     * @param position   должность (не пусто)
     * @param department отдел (не пусто)
     * @param salary     оклад (больше 0)
     */
    public Employee(long id, String fullName, String position, String department, double salary) {
        this.id = id;
        setFullName(fullName);
        setPosition(position);
        setDepartment(department);
        setSalary(salary);
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
        this.fullName = Validation.requireNotBlank(fullName, "ФИО");
    }

    public String getPosition() {
        return position;
    }

    public final void setPosition(String position) {
        this.position = Validation.requireNotBlank(position, "Должность");
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

    /** @return Иванов И.И. — инженер (Отдел разработки), оклад 120000.00 руб. */
    public String getDescription() {
        return String.format(Locale.US, "%s — %s (%s), оклад %.2f руб.",
                fullName, position, department, salary);
    }

    /**
     * Сотрудники равны, если совпадают ФИО, должность, отдел и оклад.
     * Идентификатор не учитывается.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee that = (Employee) o;
        return Double.compare(salary, that.salary) == 0
                && Objects.equals(fullName, that.fullName)
                && Objects.equals(position, that.position)
                && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, position, department, salary);
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
