package com.university.lab03.vehicle;

import com.university.lab03.util.Validation;

/**
 * Мотоцикл: расход базового × 0.7.
 */
public class Motorcycle extends Vehicle implements Informative {

    private static final double CONSUMPTION_FACTOR = 0.7;

    private String engineType;

    public Motorcycle(String brand, String model, int speed, String engineType) {
        super(brand, model, speed);
        setEngineType(engineType);
    }

    public String getEngineType() {
        return engineType;
    }

    public final void setEngineType(String engineType) {
        this.engineType = Validation.requireNotBlank(engineType, "Тип двигателя");
    }

    @Override
    public double getFuelConsumption() {
        return super.getFuelConsumption() * CONSUMPTION_FACTOR;
    }

    /** @return тип двигателя */
    @Override
    public String getInfo() {
        return "Мотоцикл " + getBrand() + " " + getModel() + ": двигатель — " + engineType;
    }

    @Override
    public String toString() {
        return super.toString() + ", мотоцикл";
    }
}
