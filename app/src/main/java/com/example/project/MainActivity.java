package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.project.DrawView.DrawView;

public class MainActivity extends AppCompatActivity {

    private DrawView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int caseNumber = getIntent().getIntExtra("Case_number", 3);
        int screenWidth =  metrics.widthPixels;

        drawingView = (DrawView) findViewById(R.id.drawView);
        drawingView.setCASE_NUMBER(caseNumber);
        drawingView.setScreenWidth(screenWidth);

        /*ConstraintLayout cl= (ConstraintLayout) findViewById(R.id.launchGame);
        switch (caseNumber){
            case 3:
                cl.setBackgroundResource(R.drawable.paint);
                break;
            case 4:
                cl.setBackgroundResource(R.drawable.sary);
                break;
            default:
                cl.setBackgroundColor(Color.RED);
        }*/

    }
}