package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Recipe;

import java.util.ArrayList;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.viewHolder> {
    interface recipeClickListener{
         void recipeClicked(Recipe recipe);
    }

    private final Context myContext;
    public ArrayList<Recipe> recipes;
    recipeClickListener listener;

    public RecipeRecyclerAdapter(Context context, ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        this.myContext = context;
        listener = (recipeClickListener)context;
    }

    @NonNull
    @Override
    public RecipeRecyclerAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(myContext).inflate(R.layout.recycler_view_list_row, parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.name.setText(recipe.getName());
        if(position%2 == 0){
            holder.recipeRow.setBackgroundColor(Color.parseColor("#fde0dc"));
        }else {
            holder.heartBtn.setBackgroundColor(Color.parseColor("#fde0dc"));
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView name;
       LinearLayout recipeRow;
       ImageButton heartBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipeName);
            recipeRow = itemView.findViewById(R.id.recipe_row);
            heartBtn = itemView.findViewById(R.id.heartBtn);
            itemView.setOnClickListener(this);
            heartBtn.setOnClickListener(view -> heartBtn.setImageResource(R.drawable.heart));
        }

        @Override
        public void onClick(View view) {
            Recipe recipe = recipes.get(getAdapterPosition());
            listener.recipeClicked(recipe);
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}