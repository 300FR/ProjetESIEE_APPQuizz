package com.example.projetesiee.controller;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.projetesiee.R;
import com.example.projetesiee.model.QuestionMapsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class QuestionMapsActivity extends QuestionActivity {

    private TextView description;
    private String secretCountryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_maps);

        this.description=findViewById(R.id.map_description_textview);
        TextView question = findViewById(R.id.map_question_textview);

        secretCountryName=getCountryNameToFind();
        question.setText(getString(R.string.question_map,secretCountryName));

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        QuestionMapsFragment myMapFragment = (QuestionMapsFragment) fragmentManager.findFragmentById(R.id.fragment_map);

        Objects.requireNonNull(myMapFragment).setQuestionMapsActivity(this);


        super.startTimer();
        super.setContext(this);
    }

    public void setNewCountry(String countryName){
        this.description.setText(countryName);
        if (secretCountryName.equals(countryName)){
            super.MoveToNextQuestion(this);
        }
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private String getCountryNameToFind(){
        String[] list = new String[]{
                getString(R.string.country_name_1),
                getString(R.string.country_name_2),
                getString(R.string.country_name_3),
                getString(R.string.country_name_4)
        };
        return list[new Random().nextInt(list.length)];
    }
}
