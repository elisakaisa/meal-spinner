package com.example.myapplication.logic;

import com.example.myapplication.model.Meal;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpinnerLogic {
    private final List<String> proteinList = Arrays.asList("Fish", "Minced meat");
    private final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes");
    private final List<String> greenList = Arrays.asList("Cucumber", "Paprika", "Green peas");

    public Meal returnRandomMeal() {
        Random rand = new Random();
        String protein = proteinList.get(rand.nextInt(proteinList.size()));
        String carb = carbList.get(rand.nextInt(carbList.size()));
        String green = greenList.get(rand.nextInt(greenList.size()));

        return new Meal(protein, carb, green);
    }

    private static SpinnerLogic spinnerLogic = null;
    public static SpinnerLogic getInstance() {
        if (spinnerLogic == null) spinnerLogic = new SpinnerLogic();
        return spinnerLogic;
    }
}
