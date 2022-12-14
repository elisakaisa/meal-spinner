package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.myapplication.database.TestFood;
import com.example.myapplication.logic.JackpotMealListener;
import com.example.myapplication.logic.Wheel;
import com.example.myapplication.model.Meal;
import com.example.myapplication.view.DeleteMealInterface;
import com.example.myapplication.view.MealAdapter;
import com.example.myapplication.viewModel.MealViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;


public class FragmentSpinner extends Fragment implements JackpotMealListener, DeleteMealInterface {

    public FragmentSpinner() {
        // Required empty public constructor
    }

    private Wheel wheel1, wheel2, wheel3;
    private MaterialButton btnSpin;
    private Handler handler, handlerNormal, handlerBonus;
    private RecyclerView recyclerView;
    private MealViewModel mealVM;

    /*--------- VARIABLES -----------*/
    private final int DELAY = 2000;
    private final int FRAME_DURATION = 100;
    private final int MAX_MEALS = 5;

    private TestFood testFoodVariables;

    /*---------- HOOKS -----------*/
    private MaterialTextView tvProtein, tvCarbs, tvGreens;

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
        MaterialButton btnReset = view.findViewById(R.id.btn_reset);
        ImageButton btnSettings = view.findViewById(R.id.img_btn_settings);
        btnSettings.setVisibility(View.INVISIBLE); //TODO: remove once actually fixed UI

        /*---------------- Meal Lists ----------------------*/
        testFoodVariables = new TestFood();

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
            btnSpin.setEnabled(meals.size() < MAX_MEALS);
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
        MealAdapter mealAdapter = new MealAdapter((ArrayList<Meal>) meals, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mealAdapter);
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
        if (containsName(testFoodVariables.bonusList, givenItem)) {
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


    private final Runnable insertMealToDatabaseAndUpdateLikelihoods = () -> {
        Meal meal = new Meal(String.valueOf(tvProtein.getText()), String.valueOf(tvCarbs.getText()), String.valueOf(tvGreens.getText()));
        mealVM.insert(meal);
        updateLikelihood(testFoodVariables.proteinList, testFoodVariables.proteinLikelihood, String.valueOf(tvProtein.getText()));
        updateLikelihood(testFoodVariables.carbList, testFoodVariables.carbLikelihood, String.valueOf(tvCarbs.getText()));
        updateLikelihood(testFoodVariables.greenList, testFoodVariables.greenLikelihood, String.valueOf(tvGreens.getText()));
        requireActivity().runOnUiThread(() -> btnSpin.setEnabled(true));
    };


    private void initWheels() {
        wheel1 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvProtein.setText(s);
        }), FRAME_DURATION, testFoodVariables.proteinList, testFoodVariables.proteinLikelihood);
        wheel2 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvCarbs.setText(s);
        }), FRAME_DURATION, testFoodVariables.carbList, testFoodVariables.carbLikelihood);
        wheel3 = new Wheel(s -> requireActivity().runOnUiThread(() -> {
            tvGreens.setText(s);
        }), FRAME_DURATION, testFoodVariables.greenList, testFoodVariables.greenLikelihood);
    }

    @Override
    public void onJackpotMeal(boolean jackpotMeal) {
        if (jackpotMeal) {
            handlerBonus.postDelayed(insertMealToDatabaseAndUpdateLikelihoods, 0);
        } else {
            handlerNormal.postDelayed(() -> wheel2.start(), 0);
            handlerNormal.postDelayed(() -> {
                wheel2.stopWheel();
                wheel3.start();
            }, DELAY);
            handlerNormal.postDelayed(() -> wheel3.stopWheel(), 2*DELAY);
            handlerNormal.postDelayed(insertMealToDatabaseAndUpdateLikelihoods, 2*DELAY + 500);
        }
    }

    private void updateLikelihood(List<String> foods, List<Double> likelihoods, String food) {
        int position = foods.indexOf(food);
        if (position != -1) {
            likelihoods.set(position, likelihoods.get(position) - 0.5);
            if (likelihoods.get(position) <= 0) likelihoods.set(position, 0.05);
        }
    }

    @Override
    public void onDeleteMeal(Meal meal) {
        mealVM.deleteMeal(meal);
    }
}