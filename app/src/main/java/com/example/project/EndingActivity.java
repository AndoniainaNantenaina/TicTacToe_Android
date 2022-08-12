package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndingActivity extends AppCompatActivity {

    public String gameEndingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        //  Prendre le nom du gagnant
        gameEndingText = getIntent().getStringExtra("goalUsernane");

        TextView mTextView = (TextView)findViewById(R.id.userGoal);
        mTextView.setText(gameEndingText);

        Button exitBtn = (Button) findViewById(R.id.buttonExit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    public void onReplayBtnClicked(View viewSource)
    {
        //  Afficher l'activité final
        Intent launchView = new Intent(this, LaunchActivity.class);
        startActivity(launchView);
    }
}