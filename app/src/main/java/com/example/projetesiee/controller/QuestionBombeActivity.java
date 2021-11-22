package com.example.projetesiee.controller;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.graphics.Canvas;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.projetesiee.model.AnimationClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionBombeActivity extends AppCompatActivity {
    private TextView timer;
    private View filBleu;
    private View filRouge;
    private ImageView couteau;
    private ImageView bombe;
    private List<View> fils;
    private FrameLayout frame;
    private AnimationClass animationClass;
    private Bitmap spriteSheet;
    private Bitmap[] frames;

    private int currentFrame=0;

    private int Time =30;

    float x=0,y=0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_question_bombe);

        this.animationClass = new AnimationClass();

        this.timer= findViewById(R.id.bombe_timer);
        this.filBleu=findViewById(R.id.bombe_blue_wire);
        this.filRouge=findViewById(R.id.bombe_red_wire);
        this.couteau=findViewById(R.id.bombe_couteau);
        this.frame=findViewById(R.id.bombe_frame);
        this.bombe=findViewById(R.id.bombe_fond);

        this.timer.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        fils= new ArrayList<View>();
        this.fils.add(this.filRouge);
        this.fils.add(this.filBleu);

        long seconde = 1000;
        long begin = 1000;
        Timer timeBombe = new Timer();
        TimerTask task = new TimerTask(){
            @Override public void run(){
                timer.setText(displayTime(Time--));
                if (Time==-1) {explode();timeBombe.cancel();timeBombe.purge();}
            }
        };
        timeBombe.scheduleAtFixedRate(task,begin,seconde);

        this.couteau.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN : {
                    x = v.getX() - event.getRawX();
                    y = v.getY() - event.getRawY();
                    return true;
                }
                case MotionEvent.ACTION_MOVE : {
                    couteau.animate()
                            .x(event.getRawX() + x)
                            .y(event.getRawY() + y)
                            .setDuration(0)
                            .start();
                    for (View vFil : fils){
                        if(isViewInBounds(vFil, (int)event.getRawX(), (int)event.getRawY())){
                            vFil.setVisibility(View.VISIBLE);
                            if (vFil.getId()==R.id.bombe_blue_wire){
                                explode();timeBombe.cancel();timeBombe.purge();
                            }else if (vFil.getId()==R.id.bombe_red_wire){
                                timeBombe.cancel();timeBombe.purge();
                            }
                        }


                    }
                    return true;
                }
                default: return true;
            }
        });

        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        this.frame.startAnimation(aniRotate);
    }

    private void explode(){
        runOnUiThread(new Runnable() { @Override public void run() {
            timer.setVisibility(View.INVISIBLE);
            couteau.setVisibility(View.INVISIBLE);
            filBleu.setVisibility(View.INVISIBLE);
            filRouge.setVisibility(View.INVISIBLE);
        } });
        spriteSheet=BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
        int NBCol =8;
        int NBLine =6;
        int ratioWidth=spriteSheet.getWidth()/(NBCol);
        int ratioHeight=spriteSheet.getHeight()/(NBLine);
        this.frames = new Bitmap[NBCol*NBLine];
        for(int j=0; j<NBLine; j++) {
            for(int i=0; i<NBCol; i++) {
                frames[j*NBCol+i] = Bitmap.createBitmap(spriteSheet, ratioWidth*i, ratioHeight*j,ratioWidth, ratioHeight);
            }
        }

        long startTime=0;
        long delay=120;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (currentFrame>=frames.length){ timer.cancel(); timer.purge(); return; }
                runOnUiThread(new Runnable() { @Override public void run() {
                    bombe.setImageBitmap(Bitmap.createBitmap(frames[currentFrame++]));
                } });
            }
        };
        timer.scheduleAtFixedRate(task,startTime,delay);
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
