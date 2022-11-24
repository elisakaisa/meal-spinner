package com.example.myapplication.database;

import java.util.Arrays;
import java.util.List;

public class TestFood {


    public final List<String> proteinList = Arrays.asList("Fish", "Minced meat", "Chicken", "Pizza", "Palt", "Hamburger");
    public final List<String> bonusList = Arrays.asList("Pizza", "Palt", "Hamburger");
    public final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes", "Fries");
    public final List<String> greenList = Arrays.asList("Cucumber", "Paprika", "Green peas");


    public List<Double> proteinLikelihood = Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
    public List<Double> carbLikelihood = Arrays.asList(1.0, 1.0, 1.0, 1.0);
    public List<Double> greenLikelihood = Arrays.asList(1.0, 1.0, 1.0);
}
