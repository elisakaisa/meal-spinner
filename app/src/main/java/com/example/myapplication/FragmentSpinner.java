package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.database.MealDatabase;
import com.example.myapplication.logic.SpinnerLogic;
import com.example.myapplication.model.Meal;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentSpinner extends Fragment {

    public FragmentSpinner() {
        // Required empty public constructor
    }

    private SpinnerLogic spinnerLogic1, spinnerLogic2, spinnerLogic3;
    MaterialButton btnSpin;
    private boolean isStarted;
    private Handler handler;
    int i = 0;
    MealDatabase appDb;

    private final List<String> proteinList = Arrays.asList("Fish", "Minced meat");
    private final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes");
    private final List<String> greenList = Arrays.asList("Cucumber", "Paprika", "Green peas");

    /*---------- HOOKS -----------*/
    MaterialTextView tvProtein, tvCarbs, tvGreens;

    public static FragmentSpinner newInstance(String param1, String param2) {
        return new FragmentSpinner();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);
        
        /*--------- HOOKS ----------*/
        tvProtein = view.findViewById(R.id.tv_spinner_protein);
        tvCarbs = view.findViewById(R.id.tv_spinner_carbs);
        tvGreens = view.findViewById(R.id.tv_spinner_greens);
        btnSpin = view.findViewById(R.id.btn_spin);

        /*---------------- START GAME ----------------------*/
        handler = new Handler();

        appDb = MealDatabase.getInstance(requireActivity());
        
        /*-------- LISTENERS --------*/
        btnSpin.setOnClickListener(v -> spinner());
        
        return view;
    }

    private void spin1() {
        spinnerLogic1 = new SpinnerLogic(s -> requireActivity().runOnUiThread(() -> {
            tvProtein.setText(s);
        }), 200, randomLong(150, 400), proteinList);
        spinnerLogic1.start();
    }

    private void spin2() {
        spinnerLogic1.stopWheel();
        spinnerLogic2 = new SpinnerLogic(s -> requireActivity().runOnUiThread(() -> {
            tvCarbs.setText(s);
        }), 200, randomLong(150, 400), carbList);
        spinnerLogic2.start();
    }
    private void spin3() {
        spinnerLogic2.stopWheel();
        spinnerLogic3 = new SpinnerLogic(s -> requireActivity().runOnUiThread(() -> {
            tvGreens.setText(s);
        }), 200, randomLong(150, 400), greenList);
        spinnerLogic3.start();
    }
    private void stopSpinner() {
        spinnerLogic3.stopWheel();
    }


    public static final Random RANDOM = new Random();

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    private void spinner() {
        // TODO: refactor this monstrosity
        btnSpin.setEnabled(false);
        int DELAY = 2000;
        spin1();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                spin2();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        spin3();

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                stopSpinner();
                                requireActivity().runOnUiThread(() -> btnSpin.setEnabled(true));
                                AsyncTask.execute(() -> {
                                    Meal meal = new Meal("chicken", "rice", "broccoli");
                                    //appDb.mealDao().insertMeal(meal);
                                    accessDB();
                                });
                            }
                        }, DELAY);
                    }
                }, DELAY);
            }
        }, DELAY);
        accessDB();
    }

    public void accessDB() {
        AsyncTask.execute(() -> {
            List<Meal> list = appDb.mealDao().getMealList();
            Log.i("DAO", "function runs");
            Log.i("DAO", String.valueOf(list));
        });
    }

}