package com.epam.rd.java.basic.task8.entity.growingParams;

public class GrowingTips {
    private Temperature temperature;
    private Lighting lighting;
    private Watering watering;

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Lighting getLighting() {
        return lighting;
    }

    public void setLighting(Lighting lighting) {
        this.lighting = lighting;
    }

    public Watering getWatering() {
        return watering;
    }

    public void setWatering(Watering watering) {
        this.watering = watering;
    }

    @Override
    public String toString() {
        return "GrowingTips{" +
                "temperature=" + temperature +
                ", lighting=" + lighting +
                ", watering=" + watering +
                '}';
    }
}
