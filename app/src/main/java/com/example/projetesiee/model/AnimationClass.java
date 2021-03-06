package com.example.projetesiee.model;

import android.graphics.Bitmap;

import java.util.Timer;
import java.util.TimerTask;

public class AnimationClass {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime, delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    public void update() {
        long elapsed = (System.nanoTime()-startTime)/1000000;

        if(elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }

        if(currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public void start(long delay){

    }

    public Bitmap getImage(){
        return frames[currentFrame];
    }

    public int getFrame(){
        return currentFrame;
    }

    public boolean playedOnce() {
        return playedOnce;
    }

}

