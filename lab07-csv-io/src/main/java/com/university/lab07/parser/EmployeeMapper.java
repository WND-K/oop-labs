package com.university.lab07.parser;

import com.university.lab07.exceptions.CsvParseException;
import com.university.lab07.exceptions.ErrorType;
import com.university.lab07.model.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Правила разбора и записи сотрудников (вариант 2):
 * id,fullName,position,salary,department,hireDate.
 */
public class EmployeeMapper implements CsvRecordMapper<Employee> {

    private static final int FIELDS_COUNT = 6;

    /** Регулярное выражение проверяет формат yyyy-MM-dd, а LocalDate.parse — существование даты. */
    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    @Override
    public String header() {
        return "id,fullName,position,salary,department,hireDate";
    }

    @Override
    public Employee fromFields(List<String> fields) throws CsvParseException {
        if (fields.size() < FIELDS_COUNT) {
            throw new CsvParseException(ErrorType.NOT_ENOUGH_FIELDS,
                    "Недостаточно полей: ожидается " + FIELDS_COUNT + ", получено " + fields.size());
        }
        if (fields.size() > FIELDS_COUNT) {
            throw new CsvParseException(ErrorType.TOO_MANY_FIELDS,
                    "Слишком много полей: ожидается " + FIELDS_COUNT + ", получено " + fields.size());
        }

        long id = parseId(fields.get(0));
        String fullName = fields.get(1).trim();
        String position = fields.get(2).trim();
        double salary = parseSalary(fields.get(3));
        String department = fields.get(4).trim();
        LocalDate hireDate = parseDate(fields.get(5));

        if (fullName.isEmpty()) {
            throw new CsvParseException(ErrorType.EMPTY_FULL_NAME, "Пустое ФИО");
        }
        return new Employee(id, fullName, position, salary, department, hireDate);
    }

    private long parseId(String value) throws CsvParseException {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new CsvParseException(ErrorType.NON_NUMERIC_ID, "Нечисловой id: '" + value + "'", e);
        }
    }

    private double parseSalary(String value) throws CsvParseException {
        double salary;
        try {
            salary = Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new CsvParseException(ErrorType.NON_NUMERIC_SALARY, "Нечисловая зарплата: '" + value + "'", e);
        }
        if (salary <= 0) {
            throw new CsvParseException(ErrorType.NON_POSITIVE_SALARY, "Зарплата должна быть больше 0: " + salary);
        }
        return salary;
    }

    private LocalDate parseDate(String value) throws CsvParseException {
        String text = value.trim();
        if (!DATE_PATTERN.matcher(text).matches()) {
            throw new CsvParseException(ErrorType.INVALID_DATE, "Дата не в формате yyyy-MM-dd: '" + value + "'");
        }
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException e) {
            throw new CsvParseException(ErrorType.INVALID_DATE, "Несуществующая дата: '" + value + "'", e);
        }
    }

    @Override
    public List<String> toFields(Employee employee) {
        return List.of(String.valueOf(employee.id()), employee.fullName(), employee.position(),
                String.format(Locale.US, "%.2f", employee.salary()), employee.department(),
                employee.hireDate().toString());
    }
}
