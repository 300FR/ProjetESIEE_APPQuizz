package com.example.projetesiee.controller;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetesiee.R;
import com.example.projetesiee.model.LeaderboardRecycleAdapter;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_leaderboard);

        ArrayList<String[]> dataSet = new  ArrayList<String[]>();
        dataSet.add(new String[]{"1","lddùù","00:34"});
        dataSet.add(new String[]{"2","lGddùù","00:35"});
        dataSet.add(new String[]{"3","lddGùù","00:36"});
        dataSet.add(new String[]{"4","ldGdGùù","00:37"});
        dataSet.add(new String[]{"5","lddùù","00:34"});
        dataSet.add(new String[]{"6","lGddùù","00:35"});
        dataSet.add(new String[]{"7","lddGùù","00:36"});
        dataSet.add(new String[]{"8","ldGdGùù","00:37"});
        dataSet.add(new String[]{"9","lddùù","00:34"});
        dataSet.add(new String[]{"10","lGddùù","00:35"});
        dataSet.add(new String[]{"11","lddGùù","00:36"});
        dataSet.add(new String[]{"12","ldGdGùù","00:37"});


        
        this.recyclerView=findViewById(R.id.leaderboard_recycleview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new LeaderboardRecycleAdapter(dataSet));
        layoutManager.scrollToPosition(0);
        
    }
}
