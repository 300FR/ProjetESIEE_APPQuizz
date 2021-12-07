package com.example.projetesiee.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetesiee.R;

public class Welcome extends AppCompatActivity {
    private Button mCreateButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_new_user);
        mCreateButton = findViewById(R.id.welcome_create_user_button);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, CreateUser.class);
                startActivity(intent);
            }
        });
    }

}
