package com.example.projetesiee.model;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String firstName;
    private int score;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public Intent setIntent(Intent intent){
        intent.putExtra("firstname", firstName);
        intent.putExtra("score", score);
        return intent;
    }

    public void setUserWithIntent(Intent intent){
        if (intent==null) return;
        if (intent.hasExtra("firstname")){
            this.firstName = intent.getStringExtra("firstname");
            this.score = intent.getIntExtra("score",0);
        }
    }
}
