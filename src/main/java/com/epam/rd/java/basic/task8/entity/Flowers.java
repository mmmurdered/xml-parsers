package com.epam.rd.java.basic.task8.entity;

import java.util.ArrayList;
import java.util.List;

public class Flowers {
    private List<Flower> flowerList;

    public List<Flower> getFlowerList() {
        if(flowerList == null){
            flowerList = new ArrayList<>();
        }
        return flowerList;
    }
}
