package com.example.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.Meal;

@Database(entities = Meal.class, exportSchema = false, version = 1)
public abstract class MealDatabase extends RoomDatabase {
    private static final String DB_NAME = "meal_db";
    private static MealDatabase instance;

    public static synchronized MealDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MealDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract MealDao mealDao();
}
