package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.logic.SpinnerLogic;
import com.example.myapplication.model.Meal;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSpinner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSpinner extends Fragment {

    public FragmentSpinner() {
        // Required empty public constructor
    }

    private SpinnerLogic spinnerLogic;

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
        MaterialButton btnSpin = view.findViewById(R.id.btn_spin);

        /*-------- GAME LOGIC -------*/
        spinnerLogic = SpinnerLogic.getInstance(); // singleton
        
        /*-------- LISTENERS --------*/
        btnSpin.setOnClickListener(v -> spin());
        
        return view;
    }

    private void spin() {
        Meal meal = spinnerLogic.returnRandomMeal();
        tvProtein.setText(meal.getProtein());
        tvCarbs.setText(meal.getCarb());
        tvGreens.setText(meal.getGreen());
    }
}