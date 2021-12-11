package com.example.myapplication;

import com.example.myapplication.Model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class JsonService {

    // get list of Recipe with name only
    public ArrayList<Recipe> getRecipeFromJSON(String json)  {
        ArrayList<Recipe> recipeList = new ArrayList<>(0);
        try {
            JSONObject jsonRecipe = new JSONObject(json);
            JSONArray recipeArr = jsonRecipe.getJSONArray("meals");
            for (int i = 0 ; i< recipeArr.length(); i++){
                String recipeName = recipeArr.getJSONObject(i).getString("strMeal");
                Recipe recipe = new Recipe(recipeName);
                recipeList.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    // get a single recipe with all information
    public Recipe getRecipeData(String json){
        Recipe recipe = new Recipe();
        try {
            JSONObject jsonRecipe = new JSONObject(json);
            JSONObject aRecipe= jsonRecipe.getJSONArray("meals").getJSONObject(0);
            String name = aRecipe.getString("strMeal");
            String cate = aRecipe.getString("strCategory");
            String image = aRecipe.getString("strMealThumb");
            String instructions = aRecipe.getString("strInstructions");
            String ytURL = aRecipe.getString("strYoutube");
            String ingredients = "";
            for(int i =0; i<20; i++){
                String ingredient = aRecipe.getString("strIngredient"+(i+1));
                if(!ingredient.equals("")){
                    ingredients += " - " + ingredient.substring(0, 1).toUpperCase() + ingredient.substring(1) + "\n";
                }else {
                    break;
                }
            }
            recipe = new Recipe(name, cate, image, ingredients, instructions, ytURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipe;
    }
}

