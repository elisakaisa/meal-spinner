package com.example.myapplication;

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
import android.widget.ImageButton;

import com.example.myapplication.logic.JackpotMealListener;
import com.example.myapplication.logic.Wheel;
import com.example.myapplication.model.Meal;
import com.example.myapplication.view.MealAdapter;
import com.example.myapplication.viewModel.MealViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentSpinner extends Fragment implements JackpotMealListener {

    public FragmentSpinner() {
        // Required empty public constructor
    }

    private Wheel wheel1, wheel2, wheel3;
    private MaterialButton btnSpin, btnReset;
    private Handler handler, handlerNormal, handlerBonus;
    private RecyclerView recyclerView;
    private MealViewModel mealVM;

    /*--------- VARIABLES -----------*/
    private final int DELAY = 2000;
    private final int FRAME_DURATION = 100;
    private final int MAX_MEALS = 10;

    private final List<String> proteinList = Arrays.asList("Fish", "Minced meat", "Chicken", "Pizza", "Palt", "Hamburger");
    private final List<String> bonusList = Arrays.asList("Pizza", "Palt", "Hamburger");
    private final List<String> carbList = Arrays.asList("Pasta", "Rice", "Potatoes", "Fries");
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
        btnReset = view.findViewById(R.id.btn_reset);
        ImageButton btnSettings = view.findViewById(R.id.img_btn_settings);

        /*---------------- HANDLERS ----------------------*/
        handler = new Handler();
        handlerNormal = new Handler();
        handlerBonus = new Handler();

        /*---------------- VIEW MODEL ----------------------*/
        mealVM = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        
        /*-------- LISTENERS --------*/
        btnSpin.setOnClickListener(v -> spinner());
        btnReset.setOnClickListener(v -> mealVM.deleteAll());
        btnSettings.setOnClickListener(v -> MainActivity.loadFragment(requireActivity().getSupportFragmentManager(), new FragmentSettings()));

        mealVM.getMeals().observe(requireActivity(), meals -> {
            fillRecyclerView(meals);
            setButtonEnabled(meals.size());
        });
        
        return view;
    }

    @Override
    public void onDestroy () {
        handler.removeCallbacks(null);
        handlerNormal.removeCallbacks(null);
        handlerBonus.removeCallbacks(null);
        super.onDestroy ();
    }

    private void fillRecyclerView(List<Meal> meals) {
        MealAdapter mealAdapter = new MealAdapter((ArrayList<Meal>) meals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mealAdapter);
    }

    private void setButtonEnabled(int listLength) {
        if (listLength < MAX_MEALS) {
            btnReset.setEnabled(false);
            btnSpin.setEnabled(true);
        } else {
            btnReset.setEnabled(true);
            btnSpin.setEnabled(false);
        }
    }

    private void spinner() {
        btnSpin.setEnabled(false);
        initWheels();

        wheel1.start();
        handler.postDelayed(() -> wheel1.stopWheel(), DELAY);
        handler.postDelayed(condition, DELAY+500);
    }

    private final Runnable condition = () -> {
        String givenItem = String.valueOf(tvProtein.getText());
        if (containsName(bonusList, givenItem)) {
            requireActivity().runOnUiThread(() -> {
                tvCarbs.setText(givenItem);
                tvGreens.setText(givenItem);
            });
            onJackpotMeal(true);
        } else {
            onJackpotMeal(false);
        }
    };

    private boolean containsName(final List<String> list, final String name){
        return list.stream().anyMatch(o -> o.equals(name));
    }


    private final Runnable insertMealToDatabase = () -> {
        Meal meal = new Meal(String.valueOf(tvProtein.getText()), String.valueOf(tvCarbs.getText()), String.valueOf(tvGreens.getText()));
        mealVM.insert(meal);
        requireActivity().runOnUiThread(() -> btnSpin.setEnabled(true));
    };


    private void initWheels() {
        wheel1 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvProtein.setText(s);
        }), FRAME_DURATION, proteinList);
        wheel2 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvCarbs.setText(s);
        }), FRAME_DURATION, carbList);
        wheel3 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvGreens.setText(s);
        }), FRAME_DURATION, greenList);
    }

    @Override
    public void onJackpotMeal(boolean jackpotMeal) {
        if (jackpotMeal) {
            handlerBonus.postDelayed(insertMealToDatabase, 0);
        } else {
            handlerNormal.postDelayed(() -> wheel2.start(), 0);
            handlerNormal.postDelayed(() -> {
                wheel2.stopWheel();
                wheel3.start();
            }, DELAY);
            handlerNormal.postDelayed(() -> wheel3.stopWheel(), 2*DELAY);
            handlerNormal.postDelayed(insertMealToDatabase, 2*DELAY + 500);
        }

    }
}