package com.example.projetesiee.controller;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetesiee.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionBombeActivity extends AppCompatActivity {

    private TextView timer;
    private View filBleu;
    private View filRouge;
    private ImageView couteau;
    private List<View> fils;
    private FrameLayout frame;

    private int Time =20;

    float x=0,y=0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_question_bombe);

        this.timer= findViewById(R.id.bombe_timer);
        this.filBleu=findViewById(R.id.bombe_blue_wire);
        this.filRouge=findViewById(R.id.bombe_red_wire);
        this.couteau=findViewById(R.id.bombe_couteau);
        this.frame=findViewById(R.id.bombe_frame);

        this.timer.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        fils= new ArrayList<View>();
        this.fils.add(this.filRouge);
        this.fils.add(this.filBleu);

        long seconde = 1000;
        long begin = 1000;
        Timer realTime = new Timer();
        TimerTask task = new TimerTask(){
            @Override public void run(){
                timer.setText(displayTime(Time--));
                if (Time==-1) {
                    realTime.cancel();
                    realTime.purge();
                }
            }
        };
        realTime.scheduleAtFixedRate(task,begin,seconde);

        this.couteau.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN : {
                    x = v.getX() - event.getRawX();
                    y = v.getY() - event.getRawY();
                    return true;
                }
                case MotionEvent.ACTION_UP : {
                    return true;
                }
                case MotionEvent.ACTION_MOVE : {
                    couteau.animate()
                            .x(event.getRawX() + x)
                            .y(event.getRawY() + y)
                            .setDuration(0)
                            .start();
                    for (View vFil : fils){
                        if(isViewInBounds(vFil, (int)event.getRawX(), (int)event.getRawY()))
                            vFil.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                default: return true;
            }
        });

        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        this.frame.startAnimation(aniRotate);
    }

    private boolean isViewInBounds(View view, int x, int y){
        Rect rect = new Rect();
        int[] location = new int[2];
        view.getDrawingRect(rect);
        view.getLocationOnScreen(location);
        rect.offset(location[0], location[1]);
        return rect.contains(x, y);
    }

    private String displayTime(int time){
        if (time>=10) return "00:"+time;
        return "00:0"+time;
    }


}
