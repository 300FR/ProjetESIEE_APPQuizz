package com.example.projetesiee.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionText {
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public QuestionText(String question, List<String> choiceList, int answerIndex) {
        mQuestion = question;
        mChoiceList = choiceList;
        mAnswerIndex = answerIndex;
    }

    public QuestionText(int NBChoices,String dataQuestion) {
        mChoiceList= new ArrayList<>();
        int index=0;
        for (String data : dataQuestion.split("\\"+UtilGame.SEPARATOR)){
            if (index==0) mQuestion=data;
            if (index>=1 && index<=NBChoices) mChoiceList.add(data);
            if (index==NBChoices+1) mAnswerIndex=Integer.parseInt(data);
            index++;
        }
    }


    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public void setChoiceList(List<String> mChoiceList) {
        this.mChoiceList = mChoiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setAnswerIndex(int mAnswerIndex) {
        this.mAnswerIndex = mAnswerIndex;
    }
}
