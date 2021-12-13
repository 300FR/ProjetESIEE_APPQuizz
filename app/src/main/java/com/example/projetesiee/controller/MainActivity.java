package com.example.projetesiee.controller;

import static java.util.Collections.shuffle;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetesiee.R;
import com.example.projetesiee.model.DBOpenHelper;
import com.example.projetesiee.model.QuestionBank;
import com.example.projetesiee.model.User;
import com.example.projetesiee.model.UtilGame;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GAME_ACTIVITY = 11;

    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final String SHARED_PREF_USER_INFO_LANGUAGE = "SHARED_PREF_LANGUAGE";
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private static final String SHARED_PREF_USER_INFO_SCORE = "SHARED_PREF_USER_INFO_SCORE";
    private static final String SHARED_PREF_USER_INFO_BEST_SCORE = "SHARED_PREF_USER_INFO_BEST_SCORE";

    private TextView mGreetingTextView;
    private Button mCreateUserButton;
    private Button mDeleteUserButton;
    private TextView mChangeUserTextView;
    private ImageButton mLeaderboardButton;
    private Button mPlayButton;
    private Spinner mUserSpinner;
    private Spinner mSpinnerLangues;


    private DBOpenHelper dbOpenHelper;

    private final String[] langues = {"en","fr","us"};

    private User mUser;

    @Override @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbOpenHelper = new DBOpenHelper(getBaseContext());

        //dbOpenHelper.insertUser("b", "2021-11-07");

        mUser = dbOpenHelper.getLastUser();

        if(mUser == null){
            Intent intent = new Intent(MainActivity.this, Welcome.class);
            startActivity(intent);
            this.finish();
            return;
        }


        setContentView(R.layout.main_screen_with_user);

        setScreenCenter();

        mGreetingTextView = findViewById(R.id.Welcome_back);
        mCreateUserButton = findViewById(R.id.create_user_button);
        mDeleteUserButton = findViewById(R.id.delete_user_button);
        mPlayButton = findViewById(R.id.main_button_play);
        mChangeUserTextView = findViewById(R.id.not_user_text);
        mLeaderboardButton = findViewById(R.id.leaderboardButton);
        mUserSpinner = findViewById(R.id.user_spinner);
        mSpinnerLangues = findViewById(R.id.spinner_langues);

        updateUserNameWithSelection();

        mUserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                String user = (String) adapter.getItem(position);

                if (user.equals(mUser.getUsername())) return;

                mUser = dbOpenHelper.getUser(user);
                updateUserNameWithSelection();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        mSpinnerLangues.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_item,langues));
        String langueSave=getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_LANGUAGE, null);
        if (langueSave!=null) mSpinnerLangues.setSelection(getIndexLangue(langueSave));

        mSpinnerLangues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Adapter adapter = parent.getAdapter();
                String langue = (String) adapter.getItem(position);
                String langueSave=getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_LANGUAGE, null);
                if (langueSave!=null && langue.equals(langueSave)) return;

                Locale locale = new Locale(langue);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).edit()
                        .putString(SHARED_PREF_USER_INFO_LANGUAGE, langue).apply();

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Class> activityClasses = new ArrayList<>();
                activityClasses.add(QuestionTableauActivity.class);
                activityClasses.add(QuestionBombeActivity.class);
                activityClasses.add(QuestionMusicActivity.class);
                activityClasses.add(QuestionMapsActivity.class);
                shuffle(activityClasses);
                activityClasses.add(QuestionCadenasActivity.class);
                QuestionBank.setActivityClasses(activityClasses);

                Intent intent = new Intent(MainActivity.this, QuestionBank.getCurrentQuestion());
                intent.putExtra(UtilGame.KEY_CURRENT_TIME, "" + 0);
                startActivity(intent);
            }
        });

        mCreateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO

                Intent intent = new Intent(MainActivity.this, CreateUser.class);
                startActivity(intent);
                /*

                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                        .edit()
                        .putString(SHARED_PREF_USER_INFO_NAME, "")
                        .apply();
                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                        .edit()
                        .putInt(SHARED_PREF_USER_INFO_BEST_SCORE, 0)
                        .apply();
                sameUser();

                */
            }
        });
        mDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbOpenHelper.deleteUser(mUser);
                mUser = dbOpenHelper.getLastUser();
                if(mUser == null){
                    Intent intent = new Intent(MainActivity.this, Welcome.class);
                    startActivity(intent);
                    return;
                }
                updateUserNameWithSelection();
            }
        });
        mLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });


        /*
        ArrayList array_list = dbOpenHelper.getAllCotacts();
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);

        //dbOpenHelper.insertContact("coco","06","lol@","4bis","paris");

        Cursor rs = dbOpenHelper.getData(0);
        rs.moveToFirst();
        
        String nam = rs.getString(rs.getColumnIndex(DBOpenHelper.CONTACTS_COLUMN_NAME));
        String phon = rs.getString(rs.getColumnIndex(DBOpenHelper.CONTACTS_COLUMN_PHONE));
        String emai = rs.getString(rs.getColumnIndex(DBOpenHelper.CONTACTS_COLUMN_EMAIL));
        String stree = rs.getString(rs.getColumnIndex(DBOpenHelper.CONTACTS_COLUMN_STREET));
        String plac = rs.getString(rs.getColumnIndex(DBOpenHelper.CONTACTS_COLUMN_CITY));

        if (!rs.isClosed())  {
            rs.close();
        }
        */
    }
    protected void updateUserNameWithSelection() {
        mUserSpinner.setSelection(updateUserName().indexOf(mUser.getUsername()));
    }


    protected  ArrayList<String> updateUserName() {
        mGreetingTextView.setText(getString(R.string.Welcome_back_name, mUser.getUsername()));
        mChangeUserTextView.setText(getString(R.string.main_not_user, mUser.getUsername()));
        ArrayList<String> userList = dbOpenHelper.getAllUsers();
        mUserSpinner.setAdapter(new ArrayAdapter<>(this,R.layout.spinner_item, userList));
        return userList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Boolean gameSucceed=intent.getBooleanExtra(UtilGame.KEY_RETURN_TO_MAIN,false);
        if (gameSucceed){
            int score = intent.getIntExtra(UtilGame.KEY_CURRENT_TIME,0);
            mUser.setScore(score);
            dbOpenHelper.updateUser(mUser);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE_GAME_ACTIVITY == requestCode && RESULT_OK == resultCode && data != null) {
           int score = data.getIntExtra(QuestionMusicActivity.BUNDLE_EXTRA_SCORE, 0);

            getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                    .edit()
                    .putInt(SHARED_PREF_USER_INFO_SCORE, score)
                    .apply();
            //sameUser();
        }
    }

    private int getIndexLangue(String s){
        int index=0;
        for(String l : langues){
            if (s.equals(l)) break;
            index++;
        }
        if (index==langues.length) index=0;
        return index;
    }
/*
    private void sameUser() {
        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);
        int score = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_SCORE, -1);
        int bestScore=getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_BEST_SCORE, 0);

        if (firstName!=null && !firstName.isEmpty()) {
            if (score != -1) {
                mGreetingTextView.setText(getString(R.string.main_welcome_name) +" "+ firstName +" "+ getString(R.string.main_welcome_score) +" "+ score);
                if (bestScore<score){
                    bestScore=score;
                    getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).edit()
                            .putInt(SHARED_PREF_USER_INFO_BEST_SCORE, score).apply();
                }
            } else {
                mGreetingTextView.setText(getString(R.string.main_welcome_name) +" "+ firstName);
            }
            mNameEditText.setSelection(mNameEditText.getText().length());
            mPlayButton.setEnabled(true);
        }else{
            firstName="";
            mPlayButton.setEnabled(false);
            mGreetingTextView.setText(getString(R.string.main_welcome));
        }
        mNameEditText.setText(firstName);
        mUserBestScore.setText(""+bestScore);
        mUserName.setText(firstName);
    }*/

    private void setScreenCenter(){
        Display display = ((WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        UtilGame.centerWidth=size.x/2;
        UtilGame.centerHeight=size.y/2;
    }

    private void updateUserScore(User user){
        dbOpenHelper.updateUser(user);
    }

}