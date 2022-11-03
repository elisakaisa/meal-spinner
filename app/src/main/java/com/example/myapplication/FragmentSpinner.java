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

import com.example.myapplication.logic.Wheel;
import com.example.myapplication.model.Meal;
import com.example.myapplication.view.MealAdapter;
import com.example.myapplication.viewModel.MealViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentSpinner extends Fragment {

    public FragmentSpinner() {
        // Required empty public constructor
    }

    private Wheel wheel1, wheel2, wheel3;
    private MaterialButton btnSpin, btnReset;
    private Handler handler;
    private RecyclerView recyclerView;
    private MealViewModel mealVM;

    /*--------- VARIABLES -----------*/
    private final int DELAY = 2000;
    private final int FRAME_DURATION = 100;
    private final int MAX_MEALS = 10;
    private boolean bonusMeal;

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

        /*---------------- START GAME ----------------------*/
        handler = new Handler();

        /*---------------- VIEW MODEL ----------------------*/
        mealVM = new ViewModelProvider(requireActivity()).get(MealViewModel.class);
        
        /*-------- LISTENERS --------*/
        btnSpin.setOnClickListener(v -> spinner());
        btnReset.setOnClickListener(v -> reset());

        mealVM.getMeals().observe(requireActivity(), meals -> {
            fillRecyclerView(meals);
            setButtonEnabled(meals.size());
        });
        
        return view;
    }

    private void reset() {
        mealVM.deleteAll();
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
        bonusMeal = false;
        btnSpin.setEnabled(false);
        initWheels();

        wheel1.start();
        handler.postDelayed(() -> wheel1.stopWheel(), DELAY);
        handler.postDelayed(condition, DELAY+500);

        handler.postDelayed(wheel2Start, DELAY+500);
        handler.postDelayed(wheel2To3, 2*DELAY+500);
        handler.postDelayed(wheel3ToEnd, 3*DELAY+500);
        handler.postDelayed(insertMealToDatabase, 3*DELAY+1000+500);


    }

    private final Runnable condition = () -> {
        //TODO: generalize to all bonus meals
        String givenItem = String.valueOf(tvProtein.getText());
        //if (givenItem.equals("Pizza")) {
        if (containsName(bonusList, givenItem)) {
            requireActivity().runOnUiThread(() -> {
                tvCarbs.setText(givenItem);
                tvGreens.setText(givenItem);
            });
            bonusMeal = true;
        }
    };

    public boolean containsName(final List<String> list, final String name){
        return list.stream().anyMatch(o -> o.equals(name));
    }

    private final Runnable wheel2Start = () -> {
        if (!bonusMeal) {
            wheel2.start();
        }
    };

    private final Runnable wheel2To3 = () -> {
        if (!bonusMeal) {
            wheel2.stopWheel();
            wheel3.start();
        }
    };

    private final Runnable wheel3ToEnd = () -> {
        if (!bonusMeal) {
            wheel3.stopWheel();
        }
    };

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

}