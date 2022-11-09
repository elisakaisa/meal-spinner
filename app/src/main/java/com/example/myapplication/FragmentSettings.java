package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.model.FoodElement;
import com.example.myapplication.model.Meal;
import com.example.myapplication.view.FoodAdapter;
import com.example.myapplication.view.MealAdapter;
import com.example.myapplication.viewModel.FoodViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSettings extends Fragment {

    FoodViewModel foodVM;

    TextInputEditText etJackpotMeals, etProtein, etCarbs, etGreens;

    public FragmentSettings() {
        // Required empty public constructor
    }

    public static FragmentSettings newInstance() {
        return new FragmentSettings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        /*---------- HOOKS -----------*/
        RecyclerView rvJackpotMeals = view.findViewById(R.id.rv_jackpot);
        RecyclerView rvProtein = view.findViewById(R.id.rv_protein);
        RecyclerView rvCarbs = view.findViewById(R.id.rv_carbs);
        RecyclerView rvGreens = view.findViewById(R.id.rv_greens);
        etJackpotMeals = view.findViewById(R.id.et_jackpot);
        MaterialButton btnAddJackpot = view.findViewById(R.id.btn_add_jackpot);

        /*---------- VIEW MODEL --------*/
        foodVM = new ViewModelProvider(requireActivity()).get(FoodViewModel.class);

        /*-------- LISTENERS -------*/
        foodVM.getJackpotMeals().observe(requireActivity(), jackpotMeals -> {
            fillRecyclerView(rvJackpotMeals, jackpotMeals);
        });

        btnAddJackpot.setOnClickListener(v -> {
            FoodElement newJackpotMeal = new FoodElement(String.valueOf(etJackpotMeals.getText()), true);
            foodVM.addJackpotMeal(newJackpotMeal);
        });

        return view;
    }

    private void fillRecyclerView(RecyclerView rv, List<FoodElement> food) {
        FoodAdapter foodAdapter = new FoodAdapter((ArrayList<FoodElement>) food);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }
}