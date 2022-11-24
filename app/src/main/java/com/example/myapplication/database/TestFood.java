package com.example.myapplication.database;

import java.util.Arrays;
import java.util.List;

public class TestFood {


    public final List<String> proteinList = Arrays.asList("Fish", "Minced meat", "Chicken", "Pizza", "Palt", "Hamburger");
    //public List<Double> proteinLikelihood = Arrays.asList(0.0, 1.0/proteinList.size(), 2*1.0/proteinList.size(), 3*1.0/proteinList.size(), 4*1.0/proteinList.size(), 5*1.0/proteinList.size());
    public List<Double> proteinLikelihood = Arrays.asList(1.0/proteinList.size(), 1.0/proteinList.size(), 1.0/proteinList.size(), 1.0/proteinList.size(), 1.0/proteinList.size(), 1.0/proteinList.size());
    public final List<String> bonusList = Arrays.asList("Pizza", "Palt", "Hamburger");
    public final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes", "Fries");
    //public List<Double> carbLikelihood = Arrays.asList(0.0, 0.25, 0.5, 0.75);
    public List<Double> carbLikelihood = Arrays.asList(0.25, 0.25, 0.25, 0.25);
    public final List<String> greenList = Arrays.asList("Cucumber", "Paprika", "Green peas");
    //public List<Double> greenLikelihood = Arrays.asList(0.0, 1.0/greenList.size(), 2*1.0/greenList.size());
    public List<Double> greenLikelihood = Arrays.asList(1.0/greenList.size(), 1.0/greenList.size(), 1.0/greenList.size());
}
