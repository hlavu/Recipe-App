package com.example.myapplication.Database;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.example.myapplication.Model.Recipe;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {
    static RecipeDatabase db;
    ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);
    Handler db_handler = new Handler(Looper.getMainLooper());

    public interface DatabaseListener {
        void databaseAllRecipeListener(List<Recipe> list);
        void databaseARecipeListener(Recipe recipe);
        void databaseExistListener(Boolean result);
    }

    public DatabaseListener listener;

    private static void BuildDBInstance (Context context) {
        db = Room.databaseBuilder(context, RecipeDatabase.class, "recipe_db").build();
    }

    public static RecipeDatabase getDBInstance(Context context){
        if(db == null){
            BuildDBInstance(context);
        }
        return db;
    }

    public void insertNewRecipe(Recipe recipe){
        databaseExecutor.execute(() -> db.getRecipeDAO().insertNewRecipe(recipe));
    }

    public void deleteAllRecipe(){
        databaseExecutor.execute(() -> db.getRecipeDAO().deleteAllRecipe());
    }

    public void getAllRecipe(){
        databaseExecutor.execute(() -> {
            List<Recipe> list =  db.getRecipeDAO().getAll();
            db_handler.post(() -> listener.databaseAllRecipeListener(list));
        });
    }



    public void doesExist(String name){
        databaseExecutor.execute(() -> {
            Boolean result =  db.getRecipeDAO().doesExist(name);
            db_handler.post(() -> listener.databaseExistListener(result));
        });
    }

    public void getARecipe(String name){
        databaseExecutor.execute(() -> {
            Recipe recipe =  db.getRecipeDAO().getARecipe(name);
            db_handler.post(() -> listener.databaseARecipeListener(recipe));
        });
    }

}
