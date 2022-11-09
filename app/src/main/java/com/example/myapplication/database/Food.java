package com.example.myapplication.database;

import com.example.myapplication.model.FoodElement;

import java.io.Serializable;
import java.util.ArrayList;

public class Food implements Serializable{

    public Food() {}

    private ArrayList<FoodElement> jackpotMealList, proteinList, carbsList, greensList;

    public ArrayList<FoodElement> getJackpotMealList() {
        if (jackpotMealList == null) jackpotMealList = new ArrayList<>();
        return jackpotMealList;
    }

    public void setJackpotMealList(ArrayList<FoodElement> jackpotMealList) {
        this.jackpotMealList = jackpotMealList;
    }

    public void addJackpotMeal(FoodElement jackpotMeal) {
        if (jackpotMealList == null) jackpotMealList = new ArrayList<>();
        jackpotMealList.add(jackpotMeal);
    }

    public ArrayList<FoodElement> getProteinList() {
        if (proteinList == null) proteinList = new ArrayList<>();
        return proteinList;
    }

    public void setProteinList(ArrayList<FoodElement> proteinList) {
        this.proteinList = proteinList;
    }

    public void addProteinList(FoodElement jackpotMeal) {
        if (proteinList == null) proteinList = new ArrayList<>();
        proteinList.add(jackpotMeal);
    }

    public ArrayList<FoodElement> getCarbsList() {
        if (carbsList == null) carbsList = new ArrayList<>();
        return carbsList;
    }

    public void setCarbsList(ArrayList<FoodElement> carbsList) {
        this.carbsList = carbsList;
    }

    public void addCarbList(FoodElement carb) {
        if (carbsList == null) carbsList = new ArrayList<>();
        carbsList.add(carb);
    }

    public ArrayList<FoodElement> getGreensList() {
        if (greensList == null) greensList = new ArrayList<>();
        return greensList;
    }

    public void setGreensList(ArrayList<FoodElement> greensList) {
        this.greensList = greensList;
    }

    public void addGreensList(FoodElement green) {
        if (greensList == null) greensList = new ArrayList<>();
        greensList.add(green);
    }
}
