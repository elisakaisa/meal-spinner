package com.example.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Meal;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private final ArrayList<Meal> mMeal;
    private final DeleteMealInterface deleteMealInterface;

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lv = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(lv);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        String protein = mMeal.get(position).getProtein();
        String carbs = mMeal.get(position).getCarb();

        if (protein.equals(carbs)) { // case bonus meal
            holder.protein.setVisibility(View.INVISIBLE);
            holder.carbs.setText(protein);
            holder.greens.setVisibility(View.INVISIBLE);
        } else { // normal case
            holder.protein.setText(protein);
            holder.carbs.setText(carbs);
            holder.greens.setText(mMeal.get(position).getGreen());
        }

        holder.btn_delete.setOnClickListener(view -> {
            deleteMealInterface.onDeleteMeal(mMeal.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mMeal.size();
    }

    public MealAdapter(ArrayList<Meal> meal, DeleteMealInterface deleteMealInterface) {
        mMeal = meal;
        this.deleteMealInterface = deleteMealInterface;
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {

        public TextView protein, carbs, greens;
        public ImageButton btn_delete;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            protein = itemView.findViewById(R.id.tv_item_protein);
            carbs = itemView.findViewById(R.id.tv_item_carbs);
            greens = itemView.findViewById(R.id.tv_item_greens);
            btn_delete = itemView.findViewById(R.id.img_btn_delete);

            itemView.setTag(this);

        }
    }
}
