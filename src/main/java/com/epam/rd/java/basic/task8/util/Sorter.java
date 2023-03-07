package com.epam.rd.java.basic.task8.util;

import com.epam.rd.java.basic.task8.entity.Flower;
import com.epam.rd.java.basic.task8.entity.Flowers;

import java.util.Comparator;

public class Sorter {
    public static final Comparator<Flower> COMPARE_BY_FLOWER_NAME = Comparator.comparing(Flower::getName);
    public static final Comparator<Flower> COMPARE_BY_FLOWER_SOIL = Comparator.comparing(Flower::getSoil);
    public static final Comparator<Flower> COMPARE_BY_FLOWER_ORIGIN = Comparator.comparing(Flower::getOrigin);

    public static void sortByName(Flowers flowers){flowers.getFlowerList().sort(COMPARE_BY_FLOWER_NAME);}
    public static void sortBySoil(Flowers flowers){flowers.getFlowerList().sort(COMPARE_BY_FLOWER_SOIL);}
    public static void sortByOrigin(Flowers flowers){flowers.getFlowerList().sort(COMPARE_BY_FLOWER_ORIGIN);}
}
