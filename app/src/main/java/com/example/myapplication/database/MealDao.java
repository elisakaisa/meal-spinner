package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.model.Meal;

import java.util.List;

@Dao
public interface MealDao {

    @Query("Select * from meal")
    List<Meal> getMealList();

    @Insert
    void insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);
}
