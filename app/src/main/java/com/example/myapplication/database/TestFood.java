package com.example.myapplication.database;

import java.util.Arrays;
import java.util.List;

public class TestFood {


    public final List<String> proteinList = Arrays.asList(  "Red meat", "Minced meat", "White fish",  "Salmon", "Chicken", "Sausage", "Meatballs",
                                                            "Parisare", "Pizza", "Palt", "Hamburger", "Tacos", "Pytt i panna", "Pasta salad");
    public final List<String> bonusList = Arrays.asList("Parisare", "Pizza", "Palt", "Hamburger", "Tacos", "Pytt i panna", "Pasta salad");
    public final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes", "Fries");
    public final List<String> greenList = Arrays.asList("Pea mix", "Cucumber", "Paprika", "Mushrooms", "Broccoli", "Cauliflower", "Salad", "Carrots");


    public List<Double> proteinLikelihood = Arrays.asList(  1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                                                            0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5);
    public List<Double> carbLikelihood = Arrays.asList(1.0, 1.0, 1.0, 1.0);
    public List<Double> greenLikelihood = Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
}
