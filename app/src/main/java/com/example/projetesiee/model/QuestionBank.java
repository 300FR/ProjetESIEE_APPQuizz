package com.example.projetesiee.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionBank {

    private static ArrayList<Class> activityClasses;
    private static int mQuestionIndex=0;

    public static void setActivityClasses(ArrayList<Class> l){
        activityClasses=l;
        mQuestionIndex=0;
    }

    public static Class getCurrentQuestion() {
        return activityClasses.get(mQuestionIndex);
    }

    public static Class getNextQuestion() {
        mQuestionIndex++;
        Log.d("tab","val="+mQuestionIndex);
        return getCurrentQuestion();
    }

    public static ArrayList<Class> getActivityClasses(){
        return activityClasses;
    }

    public static boolean isLastQuestion(){
        return mQuestionIndex==activityClasses.size()-1;
    }
}
