package com.university.lab03.model;

/**
 * Контракт «умеет формировать отчёт». Не связан с иерархией наследования.
 */
public interface Reportable {

    /** @return текст отчёта */
    String generateReport();
}
