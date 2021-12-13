package com.example.projetesiee.controller;

import static java.util.Collections.shuffle;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetesiee.R;
import com.example.projetesiee.model.DBOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateUser extends AppCompatActivity {
    private EditText mUserName;
    private EditText mBirthday;
    private Button mCreateButton;
    private DBOpenHelper dbOpenHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpenHelper = new DBOpenHelper(getBaseContext());

        setContentView(R.layout.main_create_user);

        final Calendar myCalendar = Calendar.getInstance();

        mBirthday = findViewById(R.id.Birthday);
        mUserName = findViewById(R.id.username);
        mCreateButton = findViewById(R.id.create_user);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                mBirthday.setText(sdf.format(myCalendar.getTime()));
                mBirthday.setError(null);
            }
        };

        mBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateUser.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkField()){
                    String username = mUserName.getText().toString();
                    String birthday = mBirthday.getText().toString();

                    dbOpenHelper.insertUser(username, birthday);

                    Intent intent = new Intent(CreateUser.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        });

    }

    boolean checkField(){
        boolean check = true;
        if(checkEmpty(mUserName)){
            mUserName.setError("Required");
            check = false;
        }
        if(checkEmpty(mBirthday)){
            mBirthday.setError("Required");
            check = false;
        }
        if(dbOpenHelper.getUser(mUserName.getText().toString())!=null){
            mUserName.setError("Name already taken");
            check = false;
        }
        return check;
    }

    boolean checkEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}
