package com.example.myapplication.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.Meal;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private ArrayList<Meal> mMeal;

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMeal.size();
    }

    public MealAdapter(ArrayList<Meal> meal) { mMeal = meal; }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
