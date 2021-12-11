package com.example.myapplication.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.Model.Recipe;

@Database(version = 1, entities = {Recipe.class})
public abstract class RecipeDatabase extends RoomDatabase {
    abstract public RecipeDAO getRecipeDAO();
}
