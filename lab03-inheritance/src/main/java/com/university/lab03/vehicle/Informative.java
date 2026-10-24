package com.university.lab03.vehicle;

/**
 * Контракт «умеет сообщать дополнительную информацию о себе».
 */
public interface Informative {

    /** @return дополнительная информация (зависит от типа объекта) */
    String getInfo();
}
