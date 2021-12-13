package com.example.projetesiee.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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
    protected String dataForLastQuestion;
    protected String currentTimeText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        user = new User();
        user.setIntent(intent);
        dataForLastQuestion=null;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.MinuteurView=findViewById(R.id.minuteur_view);
        this.MinuteurView.setTypeface(ResourcesCompat.getFont(this,R.font.digital_numbers));
        this.MinuteurView.setTextSize(12);
    }

    protected void startTimer(){
        startTime = Long.parseLong(getIntent().getStringExtra(UtilGame.KEY_CURRENT_TIME));
        beginTimer();
    }

    private void beginTimer() {
        currentTime=(int)startTime;
        minuteur = new Timer();
        TimerTask taskTime = new TimerTask() {@Override public void run() {
            runOnUiThread(new Runnable() { @Override public void run() {
                runInTimer();
            } });
        }};
        minuteur.scheduleAtFixedRate(taskTime,startTime,delay);
    }


    protected void runInTimer(){
        currentTimeText=UtilGame.displayTime((int)startTime+ currentTime++);
        MinuteurView.setText(currentTimeText);
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
            minuteur=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (minuteur==null){
            startTime=currentTime;
            beginTimer();
        }
    }

    protected void MoveToNextQuestion(Context actual){
        Intent intent = new Intent( actual, QuestionBank.getNextQuestion());
        Intent before =getIntent();
        Bundle bundle = before.getExtras();
        if (bundle != null) {
            String key=QuestionBombeActivity.class.getName();
            if (bundle.get(key)!=null){
                intent.putExtra(key, bundle.get(key).toString());
            }
        }
        if (dataForLastQuestion!=null){
            intent.putExtra(actual.getClass().getName(),dataForLastQuestion);
        }
        intent.putExtra(UtilGame.KEY_CURRENT_TIME, "" + currentTime);
        startActivity(intent);
    }

    protected void GoBackToMain(Context actual){
        Intent intent = new Intent(actual, MainActivity.class);
        intent.putExtra(UtilGame.KEY_RETURN_TO_MAIN,false);
        startActivity(intent);
    }
}
