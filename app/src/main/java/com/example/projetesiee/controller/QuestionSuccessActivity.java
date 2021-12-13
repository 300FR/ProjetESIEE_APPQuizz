package com.example.projetesiee.controller;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetesiee.R;
import com.example.projetesiee.model.UtilGame;

public class QuestionSuccessActivity extends AppCompatActivity {

    private int time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_question_success);

        Button backMain = findViewById(R.id.success_back_button);
        TextView textBravo = findViewById(R.id.success_bravo);

        time = (int) Long.parseLong(getIntent().getStringExtra(UtilGame.KEY_CURRENT_TIME));

        textBravo.setText(getString(R.string.success_mission,
                getString(R.string.success_temps,UtilGame.displayTime(time))));
        backMain.setText(getString(R.string.success_back));

        backMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionSuccessActivity.this, MainActivity.class);
                intent.putExtra(UtilGame.KEY_RETURN_TO_MAIN,true);
                intent.putExtra(UtilGame.KEY_CURRENT_TIME,time);
                startActivity(intent);
            }
        });


    }
}
