package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void launch3x3(View viewSource) {
        Intent drawingView = new Intent(this, MainActivity.class);
        drawingView.putExtra("Case_number", 3);
        startActivity(drawingView);
    }

    public void launch4x4(View viewSource) {
        Intent drawingView = new Intent(this, MainActivity.class);
        drawingView.putExtra("Case_number", 4);
        startActivity(drawingView);
    }
}