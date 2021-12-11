package com.example.myapplication.Database;

import androidx.room.Dao;
//import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.Model.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {
    @Insert
    void insertNewRecipe(Recipe recipe);

//    @Delete
//    void deleteRecipe(Recipe deleteRecipe);

    @Query("DELETE FROM Recipe")
    void deleteAllRecipe();

    @Query("SELECT * FROM Recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM Recipe WHERE name = :name")
    Recipe getARecipe(String name);

    @Query("SELECT EXISTS(SELECT 1 FROM Recipe WHERE name= :name)")
    boolean doesExist(String name);
}
