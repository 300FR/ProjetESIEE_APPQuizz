package com.example.projetesiee.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetesiee.R;
import com.example.projetesiee.model.User;
import com.example.projetesiee.model.UtilGame;

public class QuestionSuccessActivity extends AppCompatActivity {

    private Button backMain;
    private TextView textBravo;
    private int time;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_success);

        backMain=findViewById(R.id.success_back_button);
        textBravo=findViewById(R.id.success_bravo);

        time = (int) Long.parseLong(getIntent().getStringExtra(UtilGame.KEY_CURRENT_TIME));

        textBravo.setText("Bravo vous avez réussi !\n " +
                "Votre temps final est : "+UtilGame.displayTime(time));

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
