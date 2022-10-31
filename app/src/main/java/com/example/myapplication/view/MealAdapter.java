package com.example.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Meal;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private ArrayList<Meal> mMeal;

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lv = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(lv);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        holder.protein.setText(mMeal.get(position).getProtein());
        holder.carbs.setText(mMeal.get(position).getCarb());
        holder.greens.setText(mMeal.get(position).getGreen());
    }

    @Override
    public int getItemCount() {
        return mMeal.size();
    }

    public MealAdapter(ArrayList<Meal> meal) { mMeal = meal; }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        public TextView protein, carbs, greens;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            protein = itemView.findViewById(R.id.tv_item_protein);
            carbs = itemView.findViewById(R.id.tv_item_carbs);
            greens = itemView.findViewById(R.id.tv_item_greens);

            itemView.setTag(this);

        }
    }
}