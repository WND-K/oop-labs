package com.university.lab03.vehicle;

import com.university.lab03.util.Validation;

/**
 * Легковой автомобиль: расход базового × 1.2.
 */
public class Car extends Vehicle implements Informative {

    private static final double CONSUMPTION_FACTOR = 1.2;

    private String bodyType;

    public Car(String brand, String model, int speed, String bodyType) {
        super(brand, model, speed);
        setBodyType(bodyType);
    }

    public String getBodyType() {
        return bodyType;
    }

    public final void setBodyType(String bodyType) {
        this.bodyType = Validation.requireNotBlank(bodyType, "Тип кузова");
    }

    @Override
    public double getFuelConsumption() {
        return super.getFuelConsumption() * CONSUMPTION_FACTOR;
    }

    /** @return тип кузова */
    @Override
    public String getInfo() {
        return "Автомобиль " + getBrand() + " " + getModel() + ": кузов — " + bodyType;
    }

    @Override
    public String toString() {
        return super.toString() + ", легковой";
    }
}
