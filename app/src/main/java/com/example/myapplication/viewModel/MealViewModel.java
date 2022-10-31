package com.example.myapplication.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.database.MealDatabase;
import com.example.myapplication.model.Meal;
import com.example.myapplication.view.MealAdapter;

import java.util.ArrayList;
import java.util.List;

public class MealViewModel extends AndroidViewModel {
    
    public MutableLiveData<List<Meal>> meals;
    private MealDatabase appDb;
    private Application application;

    public MealViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        appDb = MealDatabase.getInstance(application);
    }

    public LiveData<List<Meal>> getMeals() {
        if (meals == null) {
            meals = new MutableLiveData<>();
            loadMeals();
        }
        return meals;
    }

    private void loadMeals() {
        // FIXME: https://google-developer-training.github.io/android-developer-fundamentals-course-concepts-v2/unit-4-saving-user-data/lesson-10-storing-data-with-room/10-1-c-room-livedata-viewmodel/10-1-c-room-livedata-viewmodel.html
        AsyncTask.execute(() -> {
            List<Meal> list = appDb.mealDao().getMealList();
            meals.setValue(list);
        });
    }
}
