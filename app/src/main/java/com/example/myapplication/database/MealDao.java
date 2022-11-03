package com.example.myapplication.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.model.Meal;

import java.util.List;

@Dao
public interface MealDao {

    @Query("Select * from meal")
    LiveData<List<Meal>> getMealList();

    // Simple query that does not take parameters and returns nothing.
    @Query("DELETE FROM meal")
    void deleteAll();

    @Insert
    void insertMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);
}
