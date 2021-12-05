package com.example.projetesiee.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetesiee.R;
import com.example.projetesiee.model.QuestionBank;
import com.example.projetesiee.model.User;
import com.example.projetesiee.model.UtilGame;

import java.util.Timer;
import java.util.TimerTask;

public class QuestionActivity extends AppCompatActivity {

    protected long startTime;
    protected int currentTime;
    protected final long delay=1000;
    protected TextView MinuteurView;
    protected Timer minuteur;
    protected User user;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_music);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        user = new User();
        user.setIntent(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.MinuteurView=findViewById(R.id.minuteur_view);
    }

    protected void startTimer(){
        startTime = Long.parseLong(getIntent().getStringExtra(UtilGame.KEY_CURRENT_TIME));
        currentTime=(int)startTime;
        minuteur = new Timer(); TimerTask taskTime = new TimerTask() {@Override public void run() {
            runOnUiThread(new Runnable() { @Override public void run() {
                MinuteurView.setText(UtilGame.displayTime((int)startTime+ currentTime++));
            } });}
        };
        minuteur.scheduleAtFixedRate(taskTime,startTime,delay);
    }

    protected void setContext(Context c){
        this.context=c;
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (minuteur!=null){
            minuteur.cancel(); minuteur.purge();
        }
    }

    protected void MoveToNextQuestion(Context actual){
        Intent intent = new Intent( actual, QuestionBank.getNextQuestion());
        intent.putExtra(UtilGame.KEY_CURRENT_TIME, "" + currentTime);
        startActivity(intent);
    }

    protected void GoBackToMain(Context actual){
        Intent intent = new Intent(actual, MainActivity.class);
        intent.putExtra(UtilGame.KEY_RETURN_TO_MAIN,true);
        startActivity(intent);
    }
}
