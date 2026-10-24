package com.university.lab06.repository;

import com.university.lab06.exceptions.EmployeeNotFoundException;
import com.university.lab06.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Хранилище сотрудников в памяти с логированием операций (индивидуальное задание).
 */
public class EmployeeRepository {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepository.class);

    private final Map<Long, Employee> storage = new LinkedHashMap<>();
    private long nextId = 1;

    public Employee save(Employee employee) {
        logger.info("Сохранение сотрудника: {}", employee.getFullName());
        employee.setId(nextId++);
        storage.put(employee.getId(), employee);
        logger.info("Сотрудник успешно сохранён: id={}, fullName={}", employee.getId(), employee.getFullName());
        return employee;
    }

    /**
     * @throws EmployeeNotFoundException если сотрудника с таким id нет (checked-исключение)
     */
    public Employee findById(long id) throws EmployeeNotFoundException {
        Employee employee = storage.get(id);
        if (employee == null) {
            logger.warn("Попытка получить сотрудника с id {}", id);
            throw new EmployeeNotFoundException("Сотрудник с id " + id + " не найден");
        }
        return employee;
    }

    /**
     * @throws EmployeeNotFoundException если сотрудника с таким id нет
     */
    public void delete(long id) throws EmployeeNotFoundException {
        Employee removed = storage.remove(id);
        if (removed == null) {
            logger.warn("Попытка удалить сотрудника с id {}", id);
            throw new EmployeeNotFoundException("Сотрудник с id " + id + " не найден");
        }
        logger.info("Сотрудник успешно удалён: id={}, fullName={}", id, removed.getFullName());
    }

    public Map<Long, Employee> findAll() {
        logger.debug("Получение всех сотрудников. Количество: {}", storage.size());
        return new LinkedHashMap<>(storage);
    }
}
