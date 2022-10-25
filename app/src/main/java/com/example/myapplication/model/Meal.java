package com.example.myapplication.model;

public class Meal {
    String protein, carb, green;

    public Meal(String protein, String carb, String green) {
        this.protein = protein;
        this.carb = carb;
        this.green = green;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getCarb() {
        return carb;
    }

    public void setCarb(String carb) {
        this.carb = carb;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }
}
