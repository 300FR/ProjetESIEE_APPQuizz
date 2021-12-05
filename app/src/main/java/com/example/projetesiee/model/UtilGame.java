package com.example.projetesiee.model;

import android.graphics.Bitmap;

import java.util.Timer;
import java.util.TimerTask;

public class UtilGame {

    public static String KEY_CURRENT_TIME="KEY_CURRENT_TIME";
    public static String KEY_RETURN_TO_MAIN="KEY_RETURN_TO_MAIN";

    public static String displayTime(int time){
        if (time>=60) return format(time/60)+":"+format(time%60);
        return "00:"+format(time);
    }

    private static String format(int time){
        if (time<10) return "0"+time;
        return ""+time;
    }
}
