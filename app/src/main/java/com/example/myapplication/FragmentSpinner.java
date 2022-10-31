package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.database.MealDatabase;
import com.example.myapplication.logic.Wheel;
import com.example.myapplication.model.Meal;
import com.example.myapplication.view.MealAdapter;
import com.example.myapplication.viewModel.MealViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentSpinner extends Fragment {

    public FragmentSpinner() {
        // Required empty public constructor
    }

    private Wheel wheel1, wheel2, wheel3;
    private MaterialButton btnSpin;
    private Handler handler;
    MealDatabase appDb;
    private RecyclerView recyclerView;

    /*--------- VARIABLES -----------*/
    private final int DELAY = 2000;

    private final List<String> proteinList = Arrays.asList("Fish", "Minced meat");
    private final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes");
    private final List<String> greenList = Arrays.asList("Cucumber", "Paprika", "Green peas");

    /*---------- HOOKS -----------*/
    MaterialTextView tvProtein, tvCarbs, tvGreens;

    public static FragmentSpinner newInstance() {
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
        recyclerView = view.findViewById(R.id.recycler_view);

        /*---------------- START GAME ----------------------*/
        handler = new Handler();

        /*---------------- VIEW MODEL ----------------------*/
        MealViewModel mealVM = new ViewModelProvider(requireActivity()).get(MealViewModel.class);

        /*---------------- DATABASE ----------------------*/
        appDb = MealDatabase.getInstance(requireActivity());
        fillRecyclerView();
        
        /*-------- LISTENERS --------*/
        btnSpin.setOnClickListener(v -> spinner());

        /*mealVM.getMeals().observe(requireActivity(), meals -> {
            MealAdapter mealAdapter = new MealAdapter((ArrayList<Meal>) meals);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(mealAdapter);

        }); */
        
        return view;
    }


    public static final Random RANDOM = new Random();

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    private void spinner() {
        // TODO: refactor this monstrosity
        btnSpin.setEnabled(false);
        initWheels();

        wheel1.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after 2 seconds
                wheel1.stopWheel();
                wheel2.start();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        wheel2.stopWheel();
                        wheel3.start();

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                wheel3.stopWheel();
                                requireActivity().runOnUiThread(() -> btnSpin.setEnabled(true));

                                AsyncTask.execute(() -> {
                                    Meal meal = new Meal("chicken", "rice", "broccoli");
                                    //appDb.mealDao().insertMeal(meal);
                                });
                            }
                        }, DELAY);
                    }
                }, DELAY);
            }
        }, DELAY);
    }

    public void fillRecyclerView() {
        AsyncTask.execute(() -> {
            List<Meal> list = appDb.mealDao().getMealList();
            MealAdapter mealAdapter = new MealAdapter((ArrayList<Meal>) list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(mealAdapter);
        });
    }

    private void initWheels() {
        wheel1 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvProtein.setText(s);
        }), 200, randomLong(150, 400), proteinList);
        wheel2 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvCarbs.setText(s);
        }), 200, randomLong(150, 400), carbList);
        wheel3 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvGreens.setText(s);
        }), 200, randomLong(150, 400), greenList);
    }

}