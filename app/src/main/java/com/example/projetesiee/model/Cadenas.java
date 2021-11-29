package com.example.projetesiee.model;

import android.widget.Toast;

import com.example.projetesiee.R;

import java.util.Random;

public class Cadenas {

    private int[] codeSecret;
    public int[] startingPosition;
    private int[] actualCode;

    public Cadenas(int[] codeSecret,int[] startingPosition){
        this.codeSecret=codeSecret;
        this.startingPosition=startingPosition;
        this.actualCode=startingPosition;
    }

    public Boolean setCode(int position,int value){
        this.actualCode[position]=value;
        return isCodeRight();
    }

    public Cadenas(int[] codeSecret){
        this.codeSecret=codeSecret;
        this.startingPosition=new int[codeSecret.length];
        Random rand = new Random();
        for (int i=0;i<codeSecret.length;i++){
            this.startingPosition[i]=rand.nextInt(codeSecret.length);
        }
        this.actualCode=this.startingPosition;
    }

    public Boolean isCodeRight(){
        int index=0;
        for (int x : actualCode){
            if (x!=codeSecret[index++]) return false;
        }
        return true;
    }
}
