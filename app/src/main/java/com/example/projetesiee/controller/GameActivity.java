package com.example.projetesiee.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetesiee.R;
import com.example.projetesiee.model.Question;
import com.example.projetesiee.model.QuestionBank;

import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    private static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    private static final String BUNDLE_STATE_QUESTION_COUNT = "BUNDLE_STATE_QUESTION_COUNT";
    private static final String BUNDLE_STATE_QUESTION_BANK = "BUNDLE_STATE_QUESTION_BANK";
    private static final int LENGTH_SHORT=1200;
    private static final int LENGTH_LONG=2500;
    private static final String CLE_QUESTION_ORDER = "CLE_QUESTION_ORDER";
    private static final String CLE_QUESTION_INDEX = "CLE_QUESTION_INDEX";
    private static final String CLE_SCORE = "CLE_SCORE";

    private TextView mQuestionTextView;
    private Button mAButton;
    private Button mBButton;
    private Button mCButton;
    private Button mDButton;
    private int mScore;
    private int mRemainingQuestionCount;
    private boolean mEnableTouchEvents=true;

    private QuestionBank mQuestionBank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mAButton = findViewById(R.id.game_activity_button_1);
        mBButton = findViewById(R.id.game_activity_button_2);
        mCButton = findViewById(R.id.game_activity_button_3);
        mDButton = findViewById(R.id.game_activity_button_4);

        mAButton.setOnClickListener(this);
        mBButton.setOnClickListener(this);
        mCButton.setOnClickListener(this);
        mDButton.setOnClickListener(this);
        mQuestionTextView = findViewById(R.id.game_activity_textview_question);

        mRemainingQuestionCount = 10;
        mQuestionBank=createBank();
        displayQuestion(mQuestionBank.getCurrentQuestion());
    }

    private void displayQuestion(final Question question) {
        List<String> l = question.getChoiceList();
        mAButton.setText(l.get(0));
        mBButton.setText(l.get(1));
        mCButton.setText(l.get(2));
        mDButton.setText(l.get(3));
        mQuestionTextView.setText(question.getQuestion());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int index;
        if (v == mAButton) {
            index = 0;
        }else if (v == mBButton) {
            index = 1;
        } else if (v == mCButton) {
            index = 2;
        } else if (v == mDButton) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }
        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()) {
            Toast.makeText(this, getString(R.string.correct_message), Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, getString(R.string.incorrect_message), Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                mRemainingQuestionCount--;
                if (mRemainingQuestionCount <= 0) {
                    endGame();
                } else {
                    if (mQuestionBank.isLastQuestion()){
                        endGame();
                    }else{
                        displayQuestion(mQuestionBank.getNextQuestion());
                    }
                }
            }
        }, LENGTH_SHORT);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.final_well_done))
                .setMessage(getString(R.string.final_score) +" "+ mScore)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CLE_QUESTION_ORDER,mQuestionBank.getOrder());
        outState.putInt(CLE_QUESTION_INDEX,mQuestionBank.getIndex());
        outState.putInt(CLE_SCORE,mScore);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mScore=savedInstanceState.getInt(CLE_SCORE);
        mQuestionBank.setOrder(savedInstanceState.getString(CLE_QUESTION_ORDER));
        mQuestionBank.setIndex(savedInstanceState.getInt(CLE_QUESTION_INDEX));

    }



    QuestionBank createBank(){
        Question question1 = new Question(
                getString(R.string.history_1_Q),
                Arrays.asList(
                        getString(R.string.history_1_A),
                        getString(R.string.history_1_B),
                        getString(R.string.history_1_C),
                        getString(R.string.history_1_D)
                ),
                0
        );

        Question question2 = new Question(
                getString(R.string.history_2_Q),
                Arrays.asList(
                        getString(R.string.history_2_A),
                        getString(R.string.history_2_B),
                        getString(R.string.history_2_C),
                        getString(R.string.history_2_D)
                ),
                3
        );

        Question question3 = new Question(
                getString(R.string.history_3_Q),
                Arrays.asList(
                        getString(R.string.history_3_A),
                        getString(R.string.history_3_B),
                        getString(R.string.history_3_C),
                        getString(R.string.history_3_D)
                ),
                3
        );

        Question question4 = new Question(
                getString(R.string.history_4_Q),
                Arrays.asList(
                        getString(R.string.history_4_A),
                        getString(R.string.history_4_B),
                        getString(R.string.history_4_C),
                        getString(R.string.history_4_D)
                ),
                3
        );

        Question question5 = new Question(
                getString(R.string.history_5_Q),
                Arrays.asList(
                        getString(R.string.history_5_A),
                        getString(R.string.history_5_B),
                        getString(R.string.history_5_C),
                        getString(R.string.history_5_D)
                ),
                2
        );

        Question question6 = new Question(
                getString(R.string.history_6_Q),
                Arrays.asList(
                        getString(R.string.history_6_A),
                        getString(R.string.history_6_B),
                        getString(R.string.history_6_C),
                        getString(R.string.history_6_D)
                ),
                1
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3,
                question4, question5, question6));
    }
}