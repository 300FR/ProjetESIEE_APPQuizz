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
import java.util.Random;

public class QuestionMapsActivity extends QuestionActivity {

    private QuestionMapsFragment myMapFragment;
    private TextView description;
    private TextView question;
    private String secretCountryName;
    public static GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_maps);

        this.description=findViewById(R.id.map_description_textview);
        this.question=findViewById(R.id.map_question_textview);

        secretCountryName=getCountryNameToFind();
        this.question.setText("Pays Recherché: "+secretCountryName);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        this.myMapFragment = (QuestionMapsFragment) fragmentManager.findFragmentById(R.id.fragment_map);

        this.myMapFragment.setQuestionMapsActivity(this);


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
                "Pologne","Gabon","Autriche","Cuba"
        };
        return list[new Random().nextInt(list.length)];
    }
}
