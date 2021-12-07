package com.example.projetesiee.model;

import android.content.Intent;

public class User {

    private String username;
    private int score;
    private int bestScore;
    private String birthday;


    public User(){
        username="";
        score=0;
        bestScore=0;
        birthday = "";
    }

    public User(String username, int bestScore, String birthday){
        this.username = username;
        this.score = 0;
        this.bestScore = bestScore;
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }
    public String getBirthday() { return birthday;}
    public int getScore(){return score;}
    public int getBestScore(){return bestScore;}

    public void setUsername(String username) {
        this.username = username;
    }
    public void setScore(int score) {
        this.score = score;
        if (bestScore < score) {
            bestScore = score;
        }
    }

    public Intent setIntent(Intent intent){
        intent.putExtra("firstname", username);
        intent.putExtra("score", score);
        return intent;
    }

    public void setUserWithIntent(Intent intent){
        if (intent==null) return;
        if (intent.hasExtra("firstname")){
            this.username = intent.getStringExtra("firstname");
            this.score = intent.getIntExtra("score",0);
        }
    }


}
