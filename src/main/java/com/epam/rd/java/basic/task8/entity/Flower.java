package com.epam.rd.java.basic.task8.entity;

import com.epam.rd.java.basic.task8.entity.growingParams.GrowingTips;
import com.epam.rd.java.basic.task8.entity.visualParams.VisualParameters;

public class Flower {
    private String name;
    private String soil;
    private String origin;
    private String multiplying;
    private VisualParameters visualParameters;
    private GrowingTips growingTips;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoil() {
        return soil;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMultiplying() {
        return multiplying;
    }

    public void setMultiplying(String multiplying) {
        this.multiplying = multiplying;
    }

    public VisualParameters getVisualParameters() {
        return visualParameters;
    }

    public void setVisualParameters(VisualParameters visualParameters) {
        this.visualParameters = visualParameters;
    }

    public GrowingTips getGrowingTips() {
        return growingTips;
    }

    public void setGrowingTips(GrowingTips growingTips) {
        this.growingTips = growingTips;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "name='" + name + '\'' +
                ", soil='" + soil + '\'' +
                ", origin='" + origin + '\'' +
                ", multiplying='" + multiplying + '\'' +
                ", visualParameters=" + visualParameters +
                ", growingTips=" + growingTips +
                '}';
    }
}
