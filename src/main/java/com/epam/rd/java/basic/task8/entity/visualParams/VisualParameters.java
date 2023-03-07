package com.epam.rd.java.basic.task8.entity.visualParams;

import com.epam.rd.java.basic.task8.entity.visualParams.AveLenFlower;

public class VisualParameters {
    private String stemColour;
    private String leafColour;
    private AveLenFlower aveLenFlower;

    public String getStemColour() {
        return stemColour;
    }

    public void setStemColour(String stemColour) {
        this.stemColour = stemColour;
    }

    public String getLeafColour() {
        return leafColour;
    }

    public void setLeafColour(String leafColour) {
        this.leafColour = leafColour;
    }

    public AveLenFlower getAveLenFlower() {
        return aveLenFlower;
    }

    public void setAveLenFlower(AveLenFlower aveLenFlower) {
        this.aveLenFlower = aveLenFlower;
    }

    @Override
    public String toString() {
        return "VisualParameters{" +
                "stemColour='" + stemColour + '\'' +
                ", leafColour='" + leafColour + '\'' +
                ", aveLenFlower=" + aveLenFlower +
                '}';
    }
}
