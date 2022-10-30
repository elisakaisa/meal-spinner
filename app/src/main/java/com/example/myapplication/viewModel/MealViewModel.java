package com.example.myapplication.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Meal;

import java.util.List;

public class MealViewModel extends ViewModel {
    
    public MutableLiveData<List<Meal>> meals;
    
    public LiveData<List<Meal>> getMeals() {
        if (meals == null) {
            meals = new MutableLiveData<>();
            loadMeals();
        }
        return meals;
    }

    private void loadMeals() {
    }
}
