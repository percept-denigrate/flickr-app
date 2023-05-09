package com.example.flickrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getImageButton = findViewById(R.id.button);
        ImageView image = (ImageView) findViewById(R.id.image);
        getImageButton.setOnClickListener(new GetImageOnClickListener(image));
    }
}