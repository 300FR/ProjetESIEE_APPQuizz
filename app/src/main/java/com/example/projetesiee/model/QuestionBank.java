package com.example.projetesiee.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionBank {

    private final List<Question> mQuestionList;
    private List<Integer> mQuestionOrder;
    private int mQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;
        mQuestionIndex=0;
        mQuestionOrder= new ArrayList<Integer>();
        Collections.shuffle(mQuestionList);
        for (Question q: questionList){
            mQuestionOrder.add(mQuestionList.indexOf(q));
        }
    }

    public String getOrder(){
        return mQuestionOrder.toString();
    }

    public void setOrder(String order){
        order=order.substring(1,order.length()-1);
        mQuestionOrder= new ArrayList<Integer>();
        for (String s : order.split(",")){
            mQuestionOrder.add(Integer.parseInt(s.trim()));
        }
    }

    public int getIndex(){
        return mQuestionIndex;
    }

    public void setIndex(int index){
        mQuestionIndex=index;
    }

    public Question getCurrentQuestion() {
        return mQuestionList.get(mQuestionIndex);
    }

    public Question getNextQuestion() {
        mQuestionIndex++;
        return getCurrentQuestion();
    }

    public boolean isLastQuestion(){
        return mQuestionIndex==mQuestionList.size()-1;
    }
}
