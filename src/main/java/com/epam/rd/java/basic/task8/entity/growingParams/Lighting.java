package com.epam.rd.java.basic.task8.entity.growingParams;

public class Lighting {
    String lightRequiring;

    public String getLightRequiring() {
        return lightRequiring;
    }

    public void setLightRequiring(String lightRequiring) {
        this.lightRequiring = lightRequiring;
    }

    @Override
    public String toString() {
        return "Lighting{" +
                "lightRequiring='" + lightRequiring + '\'' +
                '}';
    }
}
