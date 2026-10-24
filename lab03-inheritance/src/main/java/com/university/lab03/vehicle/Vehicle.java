package com.university.lab03.vehicle;

import com.university.lab03.util.Validation;

/**
 * Базовый класс иерархии «Транспортные средства».
 */
public class Vehicle {

    /** Базовый расход топлива, л/100 км. */
    private static final double BASE_FUEL_CONSUMPTION = 8.0;

    private String brand;
    private String model;
    private int speed;

    /**
     * @param brand марка (не пустая)
     * @param model модель (не пустая)
     * @param speed максимальная скорость, км/ч (не отрицательная)
     */
    public Vehicle(String brand, String model, int speed) {
        setBrand(brand);
        setModel(model);
        setSpeed(speed);
    }

    public String getBrand() {
        return brand;
    }

    public final void setBrand(String brand) {
        this.brand = Validation.requireNotBlank(brand, "Марка");
    }

    public String getModel() {
        return model;
    }

    public final void setModel(String model) {
        this.model = Validation.requireNotBlank(model, "Модель");
    }

    public int getSpeed() {
        return speed;
    }

    public final void setSpeed(int speed) {
        this.speed = (int) Validation.requireNonNegative(speed, "Скорость");
    }

    /** @return базовый расход топлива, л/100 км */
    public double getFuelConsumption() {
        return BASE_FUEL_CONSUMPTION;
    }

    @Override
    public String toString() {
        return brand + " " + model + " (" + speed + " км/ч)";
    }
}
