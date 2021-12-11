package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapplication.Database.DatabaseManager;
import com.example.myapplication.Database.RecipeDatabase;
import com.example.myapplication.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeRecyclerAdapter.recipeClickListener, NetworkingService.NetworkingListener, DatabaseManager.DatabaseListener {
    ArrayList<Recipe> recipes = new ArrayList<>();
    RecyclerView recyclerView;
    NetworkingService networkingService;
    RecipeRecyclerAdapter adapter;
    JsonService jsonService;
    ImageButton searchCate;
    EditText inputCate;
    DatabaseManager dbManager;
    RecipeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkingService = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();
        networkingService.listener = this;
        recyclerView = findViewById(R.id.recipeList);
        searchCate = findViewById(R.id.searchBtn);
        inputCate = findViewById(R.id.inputCate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = DatabaseManager.getDBInstance(this);
        dbManager = ((myApp)getApplication()).getDatabaseManager();

        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList("recipes");
            adapter = new RecipeRecyclerAdapter(this, recipes);
            recyclerView.setAdapter(adapter);
        } else {
            networkingService.getRecipe();
        }

        searchCate.setOnClickListener(view -> {

            // hides soft keyboard
            hideSoftKeyboard(MainActivity.this);

            // if user provide an empty input, show toast to announce
            if (inputCate.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Please provide a food category!!!", Toast.LENGTH_SHORT).show();
            } else {
                // calls function requesting API
                networkingService.listener = this;
                networkingService.getByCategory(inputCate.getText().toString());
                inputCate.setText(""); // resets value of edit text
            }
        });
    }

    // hides soft keyboard when search btn is clicked
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    // handle single recipe clicked
    public void recipeClicked(Recipe selectedRecipe){
        Intent myIntent = new Intent(this, RecipeActivity.class);
        myIntent.putExtra("recipeName", selectedRecipe.getName());
        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.getRecipe: {
                // get list of recipes from room database
                dbManager.listener = this;
                dbManager.getAllRecipe();
                break;
            }
            case R.id.reset: {
                // delete all recipes in the database
                dbManager.deleteAllRecipe();
                break;
            }
        }
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void dataListener(String jsonString) {
        recipes = jsonService.getRecipeFromJSON(jsonString);
        adapter = new RecipeRecyclerAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
        // category is invalid
        if(recipes.size() == 0){
            Toast.makeText(getApplicationContext(), "Food category is not found!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void imageListener(Bitmap image) { }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipes", recipes);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        recipes = savedInstanceState.getParcelableArrayList("recipes");
    }

    @Override
    public void databaseAllRecipeListener(List<Recipe> list) {
        // get data from database
        recipes = new ArrayList<>(list);
        adapter = new RecipeRecyclerAdapter(this, recipes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void databaseARecipeListener(Recipe recipe) { }

    @Override
    public void databaseExistListener(Boolean result) { }
}