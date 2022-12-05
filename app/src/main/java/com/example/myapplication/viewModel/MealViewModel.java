package com.example.myapplication.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.model.Meal;
import com.example.myapplication.repository.MealRepository;

import java.util.List;

public class MealViewModel extends AndroidViewModel {

    private final MealRepository mRepository;
    private final LiveData<List<Meal>> meals;

    public MealViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MealRepository(application);
        meals = mRepository.getAllMeals();
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }

    public void insert(Meal meal) { mRepository.insert(meal); }

    public void deleteAll() { mRepository.deleteAll(); }

    public void deleteMeal(Meal meal) { mRepository.deleteMeal(meal); }

}
