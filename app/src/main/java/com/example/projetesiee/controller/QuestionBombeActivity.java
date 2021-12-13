package com.example.projetesiee.controller;

import static com.example.projetesiee.model.UtilGame.SEPARATOR;
import static java.util.Collections.shuffle;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.projetesiee.R;
import com.example.projetesiee.model.AnimationClass;
import com.example.projetesiee.model.UtilGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionBombeActivity extends QuestionActivity {

    private TextView timer;
    private TextView instruction;
    private View filBleu;
    private View filRouge;
    private View filJaune;
    private View filVert;
    private FrameLayout couteau;
    private ImageView bombe;
    private List<View> fils;
    private FrameLayout frame;
    private AnimationClass animationClass;
    private Bitmap spriteSheet;
    private Bitmap[] frames;
    private Stack<View> orderedWires;


    private HashMap<Integer,String> nameWires;

    private boolean isClickable=false;

    private int currentFrame = 0;
    private int Time = 20;

    float x = 0, y = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bombe);

        this.animationClass = new AnimationClass();

        this.timer = findViewById(R.id.bombe_timer);
        this.couteau = findViewById(R.id.bombe_frame_couteau);
        this.frame = findViewById(R.id.bombe_frame);
        this.bombe = findViewById(R.id.bombe_fond);
        this.instruction =findViewById(R.id.bombe_instruction_text);

        this.filRouge=findViewById(R.id.bombe_red_wire);
        this.filBleu=findViewById(R.id.bombe_blue_wire);
        this.filJaune=findViewById(R.id.bombe_yellow_wire);
        this.filVert=findViewById(R.id.bombe_green_wire);

        this.timer.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        this.timer.setTypeface(ResourcesCompat.getFont(this,R.font.digital_numbers));


        nameWires=new HashMap<>();
        nameWires.put(R.id.bombe_red_wire,getString(R.string.bombe_rouge));
        nameWires.put(R.id.bombe_green_wire,getString(R.string.bombe_vert));
        nameWires.put(R.id.bombe_yellow_wire,getString(R.string.bombe_jaune));
        nameWires.put(R.id.bombe_blue_wire,getString(R.string.bombe_bleu));

        fils = new ArrayList<>();
        this.fils.add(this.filRouge);
        this.fils.add(this.filBleu);
        this.fils.add(this.filJaune);
        this.fils.add(this.filVert);
        shuffle(fils);
        setOrderWires();

        setTimerBombe();

        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        this.frame.startAnimation(aniRotate);

        isClickable=true;
        super.startTimer();
        super.setContext(QuestionBombeActivity.this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setTimerBombe() {
        long seconde = 1000;
        long begin = 1000;
        Timer timeBombe = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { @Override public void run() {
                    timer.setText(UtilGame.displayTime(Time--));
                } });
                if (Time == -1) {
                    explode();
                    timeBombe.cancel();
                    timeBombe.purge();
                }
            }
        };
        timeBombe.scheduleAtFixedRate(task, begin, seconde);

        this.couteau.setOnTouchListener((v, event) -> {
            if (!isClickable) return true;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    x = v.getX() - event.getRawX();
                    y = v.getY() - event.getRawY();
                    return true;
                }
                case MotionEvent.ACTION_MOVE: {
                    couteau.animate()
                            .x(event.getRawX() + x)
                            .y(event.getRawY() + y)
                            .setDuration(0)
                            .start();

                    for (View vFil : fils) {
                        if (isViewInBounds(vFil, (int) event.getRawX(), (int) event.getRawY())) {
                            vFil.setVisibility(View.VISIBLE);
                            if (orderedWires.peek().getId()==vFil.getId()){
                                orderedWires.pop();
                                if (orderedWires.empty()){
                                    timeBombe.cancel();
                                    timeBombe.purge();
                                    isClickable=false;
                                    MoveToNextQuestion(QuestionBombeActivity.this);
                                }
                                fils.remove(vFil);
                                break;
                            }else{
                                explode();
                                timeBombe.cancel();
                                timeBombe.purge();
                                isClickable=false;
                            }
                        }
                    }
                    return true;
                }
                default:
                    return true;
            }
        });

    }

    private void explode() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer.setVisibility(View.INVISIBLE);
                instruction.setVisibility(View.INVISIBLE);
                couteau.setVisibility(View.INVISIBLE);
                for (View v :fils){
                    v.setVisibility(View.INVISIBLE);
                }
            }
        });
        spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.explosion);
        int NBCol = 8;
        int NBLine = 6;
        int ratioWidth = spriteSheet.getWidth() / (NBCol);
        int ratioHeight = spriteSheet.getHeight() / (NBLine);
        this.frames = new Bitmap[NBCol * NBLine];
        for (int j = 0; j < NBLine; j++) {
            for (int i = 0; i < NBCol; i++) {
                frames[j * NBCol + i] = Bitmap.createBitmap(spriteSheet, ratioWidth * i, ratioHeight * j, ratioWidth, ratioHeight);
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bombe.setX(UtilGame.centerWidth -ratioWidth);
                bombe.setY(UtilGame.centerHeight-ratioHeight);
            }
        });

        long startTime = 0;
        long delay = 50;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (currentFrame >= frames.length) {
                    timer.cancel();
                    timer.purge();
                    GoBackToMain(QuestionBombeActivity.this);
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bombe.setImageBitmap(Bitmap.createBitmap(frames[currentFrame++]));
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, startTime, delay);
    }

    private boolean isViewInBounds(View view, int x, int y) {
        Rect rect = new Rect();
        int[] location = new int[2];
        view.getDrawingRect(rect);
        view.getLocationOnScreen(location);
        rect.offset(location[0], location[1]);
        return rect.contains(x, y);
    }

    private void setOrderWires(){
        orderedWires= new Stack<>();
        String textForInstruction=new String();
        int index=0;
        for (View v : fils){
            textForInstruction+="/"+nameWires.get(fils.get(fils.size()-1-index++).getId());
            orderedWires.add(v);
        }
        index=(new Random()).nextInt(fils.size());
        super.dataForLastQuestion=nameWires.get(fils.get(index).getId())+SEPARATOR+index;
        this.instruction.setText(textForInstruction.substring(1));
    }
}
