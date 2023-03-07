package com.epam.rd.java.basic.task8.entity;

public class FieldImpl {
    private String measure;
    private int content;

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "FieldImpl{" +
                "measure='" + measure + '\'' +
                ", content=" + content +
                '}';
    }
}
