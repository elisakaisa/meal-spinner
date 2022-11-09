package com.example.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.FoodElement;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private ArrayList<FoodElement> mFood;

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lv = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(lv);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        String food = mFood.get(position).getFood();
        holder.tvFood.setText(food);
    }

    @Override
    public int getItemCount() {
        return mFood.size();
    }

    public FoodAdapter(ArrayList<FoodElement> food) { mFood = food; }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFood;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFood = itemView.findViewById(R.id.tv_item_food);

            itemView.setTag(this);
        }
    }
}
