package com.example.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal")
public class Meal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "protein")
    private String protein;

    @ColumnInfo(name = "carb")
    private String carb;

    @ColumnInfo(name = "green")
    private String green;

    public Meal(int id, String protein, String carb, String green) {
        this.id = id;
        this.protein = protein;
        this.carb = carb;
        this.green = green;
    }

    @Ignore
    public Meal(String protein, String carb, String green) {
        this.protein = protein;
        this.carb = carb;
        this.green = green;
    }

    public String getProtein() {
        return protein;
    }

    public String getCarb() {
        return carb;
    }

    public String getGreen() {
        return green;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
