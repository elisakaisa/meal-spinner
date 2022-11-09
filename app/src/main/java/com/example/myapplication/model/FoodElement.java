package com.example.myapplication.model;

import java.io.Serializable;

public class FoodElement implements Serializable {
    String food;
    boolean inSpinner;

    public FoodElement(String food, boolean inSpinner) {
        this.food = food;
        this.inSpinner = inSpinner;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public boolean isInSpinner() {
        return inSpinner;
    }

    public void setInSpinner(boolean inSpinner) {
        this.inSpinner = inSpinner;
    }
}
