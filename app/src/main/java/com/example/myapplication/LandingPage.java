package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LandingPage extends AppCompatActivity  {

    Button searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        // Button navigate to main activity
        searchBtn.setOnClickListener(view -> {
            Intent myIntent = new Intent(this, MainActivity.class);
                   startActivity(myIntent);
            });
    }

}