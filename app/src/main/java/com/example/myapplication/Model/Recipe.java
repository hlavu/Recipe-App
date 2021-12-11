package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Recipe implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String cate;
    public String imageURL;
    public String ingredients;
    public String instructions;
    public String youtubeURL;

    @Ignore
    public Recipe(Recipe recipe) {
        this.id = recipe.id;
        this.name = recipe.name;
        this.cate = recipe.cate;
        this.imageURL = recipe.imageURL;
        this.ingredients = recipe.ingredients;
        this.instructions = recipe.instructions;
        this.youtubeURL = recipe.youtubeURL;
    }

    @Ignore
    public String getCate() {
        return cate;
    }

    @Ignore
    public String getImageURL() {
        return imageURL;
    }

    @Ignore
    public String getIngredients() {
        return ingredients;
    }

    @Ignore
    public String getInstructions() {
        return instructions;
    }

    @Ignore
    public String getYoutubeURL() {
        return youtubeURL;
    }

    @Ignore
    public Recipe(String name) {
        this.name = name;
    }

    public Recipe(){
        this.name = "";
        this.cate = "";
        this.imageURL = "";
        this.ingredients = null;
        this.instructions = "";
    }

    @Ignore
    public Recipe(String name, String cate, String image, String ingredients, String instructions, String ytURL) {
        this.name = name;
        this.cate = cate;
        this.imageURL = image;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.youtubeURL = ytURL;
    }

    @Ignore
    protected Recipe(Parcel in) {
        name = in.readString();
        cate = in.readString();
        imageURL = in.readString();
        ingredients = in.readString();
        instructions = in.readString();
        youtubeURL = in.readString();
    }

    @Ignore
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Ignore
    public String getName() {
        return name;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(cate);
        dest.writeString(imageURL);
        dest.writeString(ingredients);
        dest.writeString(instructions);
        dest.writeString(youtubeURL);
    }
}
