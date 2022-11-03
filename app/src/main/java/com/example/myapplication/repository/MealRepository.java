package com.example.myapplication.repository;

import android.app.Application;
import android.app.AsyncNotedAppOp;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.database.MealDao;
import com.example.myapplication.database.MealDatabase;
import com.example.myapplication.model.Meal;

import java.util.List;

public class MealRepository {

    private MealDao mMealDao;
    private LiveData<List<Meal>> mAllMeals;

    public MealRepository(Application application) {
        MealDatabase db = MealDatabase.getInstance(application);
        mMealDao = db.mealDao();
        mAllMeals = mMealDao.getMealList();
    }

    public LiveData<List<Meal>> getAllMeals() {
        return mAllMeals;
    }

    public void insert(Meal meal) {
        AsyncTask.execute(() -> mMealDao.insertMeal(meal));
    }

    public void deleteAll() {
        AsyncTask.execute(() -> mMealDao.deleteAll());
    }
}
