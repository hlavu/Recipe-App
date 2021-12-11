package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Database.DatabaseManager;
import com.example.myapplication.Model.Recipe;

import java.util.List;

public class RecipeActivity extends AppCompatActivity implements NetworkingService.NetworkingListener, DatabaseManager.DatabaseListener {
    NetworkingService networkingService;
    Recipe recipe;
    JsonService jsonService;
    ImageView img;
    TextView name, cate, ingredients, instruction, ytURL;
    ImageButton download;
    boolean found;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        img =  findViewById(R.id.img);
        name = findViewById(R.id.name);
        cate = findViewById(R.id.cate);
        ingredients = findViewById(R.id.ingreContent);
        instruction = findViewById(R.id.instrucContent);
        ytURL = findViewById(R.id.ytURL);
        download = findViewById(R.id.dowloadBtn);
        dbManager = ((myApp) getApplication()).getDatabaseManager();
        dbManager.listener = this;
        networkingService = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();
        String recipeName = getIntent().getStringExtra("recipeName");
        networkingService.listener = this;

        // find in database
        dbManager.doesExist(recipeName);
        // if it exists, get data from database
        if(found){
            dbManager.getARecipe(recipeName);
        } else {
            // if not, fetch from api
            networkingService.getByName(recipeName);
        }

        download.setOnClickListener(view -> {
            // insert new recipe to database
            dbManager.insertNewRecipe(recipe);
            download.setImageResource(R.drawable.check);
        });
    }

    @Override
    public void dataListener(String jsonString) {
        recipe = jsonService.getRecipeData(jsonString);
        networkingService.getImageData(recipe.getImageURL());
        setUI(recipe);
    }

    private void setUI(Recipe recipe){
        name.setText(recipe.getName());
        cate.setText(String.format("Category: %s", recipe.getCate()));
        ingredients.setText(recipe.getIngredients());
        instruction.setText(recipe.getInstructions());
        ytURL.setText(recipe.getYoutubeURL());
    }

    @Override
    public void imageListener(Bitmap image) {
        img.setImageBitmap(image);
    }

    @Override
    public void databaseAllRecipeListener(List<Recipe> list) { }

    @Override
    public void databaseARecipeListener(Recipe recipe) {
        recipe = new Recipe(recipe);
        setUI(recipe);
    }

    @Override
    public void databaseExistListener(Boolean result) {
        found = result;
    }
}