package com.example.projetesiee.controller;

import static com.example.projetesiee.model.UtilGame.SEPARATOR;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetesiee.R;
import com.example.projetesiee.model.QuestionBank;
import com.example.projetesiee.model.QuestionText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionMusicActivity extends QuestionActivity implements View.OnClickListener {

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
    private boolean mEnableTouchEvents=true;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Timer seekBarTimer = new Timer();
    private final int RATIO_MEDIAPLAYER_SEEKBAR=1000;
    private int musicID;
    private QuestionText questionText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_music);

        musicID = getRandomMusic();

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),musicID);

        seekBar=findViewById(R.id.music_question_seekBar);

        mAButton = findViewById(R.id.game_activity_button_1);
        mBButton = findViewById(R.id.game_activity_button_2);
        mCButton = findViewById(R.id.game_activity_button_3);
        mDButton = findViewById(R.id.game_activity_button_4);
        mQuestionTextView = findViewById(R.id.music_question_textView);

        mAButton.setOnClickListener(this);
        mBButton.setOnClickListener(this);
        mCButton.setOnClickListener(this);
        mDButton.setOnClickListener(this);


        setQuestion(getQuestion(musicID));
        setSeekBar();
        setSeekBarTimer();
        start();
        super.startTimer();
        super.setContext(QuestionMusicActivity.this);
    }

    private void setSeekBar(){
        seekBar.setMax(mediaPlayer.getDuration() / RATIO_MEDIAPLAYER_SEEKBAR);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(fromUser) mediaPlayer.seekTo(progress*RATIO_MEDIAPLAYER_SEEKBAR);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {
                if (!mediaPlayer.isPlaying()) start();
            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {  }
        });
    }

    private void setSeekBarTimer(){
        long delay = 300;
        long begin = 200;
        seekBarTimer= new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { @Override public void run() {
                    if (seekBarTimer==null) return;
                    seekBar.setProgress(mediaPlayer.getCurrentPosition()/RATIO_MEDIAPLAYER_SEEKBAR);
                } });
            }
        };
        seekBarTimer.scheduleAtFixedRate(task, begin, delay);
    }

    private void start(){
        if(mediaPlayer!=null) {
            mediaPlayer.start();
        }
    }

    private void setQuestion(String dataQuestion) {
        questionText = new QuestionText(4,dataQuestion);
        List<String> l = questionText.getChoiceList();
        mAButton.setText(l.get(0));
        mBButton.setText(l.get(1));
        mCButton.setText(l.get(2));
        mDButton.setText(l.get(3));
        mQuestionTextView.setText(questionText.getQuestion());
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
        if (index ==questionText.getAnswerIndex()) {
            Toast.makeText(this, getString(R.string.correct_message), Toast.LENGTH_SHORT).show();
            super.MoveToNextQuestion(QuestionMusicActivity.this);
        } else {
            Toast.makeText(this, getString(R.string.incorrect_message), Toast.LENGTH_SHORT).show();
            super.currentTime+=10;
            //super.GoBackToMain(QuestionMusicActivity.this);
        }
    }

    @Override
    protected void onPause() {
        if (seekBarTimer != null) {
            seekBarTimer.cancel();
            seekBarTimer.purge();
            seekBarTimer=null;
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
        super.onPause();
    }

    private String getQuestion(int id){
        switch (id){
            case R.raw.billie_jean:
                return "Quel est le titre de ce clip de Michael Jackson?"+SEPARATOR+
                        "Smooth Criminal"+SEPARATOR+"Billie Jean"+SEPARATOR+
                        "Beat It"+SEPARATOR+"Thriller"+SEPARATOR+"1";
            case R.raw.lotr:
                return "De quel film célèbre provient cette musique ?"+SEPARATOR+
                        "Gladiator"+SEPARATOR+"Terminator"+SEPARATOR+
                        "Indiana Jones"+SEPARATOR+"Le Seigneur des Anneaux"+SEPARATOR+"3";
            default:
                return null;
        }
    }

    private int getRandomMusic(){
        ArrayList<Integer> musicIDs = new ArrayList<>();
        musicIDs.add(R.raw.billie_jean);
        musicIDs.add(R.raw.lotr);
        return musicIDs.get((new Random()).nextInt(musicIDs.size()));
    }

}