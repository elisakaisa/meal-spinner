package com.example.myapplication.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.MainActivity;
import com.example.myapplication.database.Food;
import com.example.myapplication.model.FoodElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    public MutableLiveData<List<FoodElement>> jackpotMeals;
    public MutableLiveData<List<FoodElement>> proteinList;
    public MutableLiveData<List<FoodElement>> carbsList;
    public MutableLiveData<List<FoodElement>> greensList;


    public FoodViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<FoodElement>> getJackpotMeals() {
        if(jackpotMeals == null) {
            jackpotMeals = new MutableLiveData<>();
            loadJackpotMeals();
        }
        return jackpotMeals;
    }

    public LiveData<List<FoodElement>> getProteinList() {
        if(proteinList == null) {
            proteinList = new MutableLiveData<>();
            loadProteins();
        }
        return proteinList;
    }

    public LiveData<List<FoodElement>> getCarbsList() {
        if(carbsList == null) {
            carbsList = new MutableLiveData<>();
            loadCarbs();
        }
        return carbsList;
    }

    public LiveData<List<FoodElement>> getGreensList() {
        if(greensList == null) {
            greensList = new MutableLiveData<>();
            loadGreens();
        }
        return greensList;
    }

    private void loadJackpotMeals() {
        try{
            FileInputStream fin = getApplication().getApplicationContext().openFileInput(MainActivity.FOOD_LIST);
            ObjectInputStream oin = new ObjectInputStream(fin);
            MainActivity.foodList = (Food) oin.readObject();
            jackpotMeals.setValue(MainActivity.foodList.getJackpotMealList());
            oin.close();

        } catch (Exception e){
            e.printStackTrace();
            MainActivity.foodList = new Food();
            jackpotMeals.setValue(MainActivity.foodList.getJackpotMealList());
        }
    }

    private void loadProteins() {
        try{
            FileInputStream fin = getApplication().getApplicationContext().openFileInput(MainActivity.FOOD_LIST);
            ObjectInputStream oin = new ObjectInputStream(fin);
            MainActivity.foodList = (Food) oin.readObject();
            proteinList.setValue(MainActivity.foodList.getProteinList());
            oin.close();

        } catch (Exception e){
            e.printStackTrace();
            MainActivity.foodList = new Food();
            proteinList.setValue(MainActivity.foodList.getProteinList());
        }
    }

    private void loadCarbs() {
        try{
            FileInputStream fin = getApplication().getApplicationContext().openFileInput(MainActivity.FOOD_LIST);
            ObjectInputStream oin = new ObjectInputStream(fin);
            MainActivity.foodList = (Food) oin.readObject();
            jackpotMeals.setValue(MainActivity.foodList.getCarbsList());
            oin.close();

        } catch (Exception e){
            e.printStackTrace();
            MainActivity.foodList = new Food();
            jackpotMeals.setValue(MainActivity.foodList.getCarbsList());
        }
    }

    private void loadGreens() {
        try{
            FileInputStream fin = getApplication().getApplicationContext().openFileInput(MainActivity.FOOD_LIST);
            ObjectInputStream oin = new ObjectInputStream(fin);
            MainActivity.foodList = (Food) oin.readObject();
            jackpotMeals.setValue(MainActivity.foodList.getGreensList());
            oin.close();

        } catch (Exception e){
            e.printStackTrace();
            MainActivity.foodList = new Food();
            jackpotMeals.setValue(MainActivity.foodList.getGreensList());
        }
    }

    public void addJackpotMeal(FoodElement food) {
        MainActivity.foodList.addJackpotMeal(food);
        writeObjectInFileOutput();
        loadJackpotMeals();
    }

    public void addProtein(FoodElement food) {
        MainActivity.foodList.addProteinList(food);
        writeObjectInFileOutput();
        loadProteins();
    }

    private void writeObjectInFileOutput() {
        try {
            FileOutputStream fos = getApplication().getApplicationContext().openFileOutput(MainActivity.FOOD_LIST, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(MainActivity.foodList);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
