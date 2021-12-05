package com.example.projetesiee.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetesiee.R;
import com.example.projetesiee.model.DBOpenHelper;
import com.example.projetesiee.model.LeaderboardRecycleAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class LeaderboardActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    private ImageButton button_back;
    private LinearLayout linearLayout;
    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_leaderboard);

        this.recyclerView=findViewById(R.id.leaderboard_recycleview);
        this.button_back=findViewById(R.id.button_leaderboard_back);
        this.linearLayout=findViewById(R.id.leaderboard_verticallayout);

        this.button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaderboardActivity.this, MainActivity.class));
            }
        });

        /*

        dbOpenHelper = new DBOpenHelper(this);
        HashMap<String,String > map = dbOpenHelper.getLeaderboard();

        Set<String> keys = map.keySet();
        ArrayList<String> l = (ArrayList<String>) keys;

        for (String key : map.keySet()){

        }*/

        ArrayList<String[]> dataSet = new  ArrayList<String[]>();
        //dataSet.add(new String[]{"1","lddùù","00:34"});
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new LeaderboardRecycleAdapter(dataSet));
        layoutManager.scrollToPosition(0);

        String[] tab = new String[]{"1","lddùù","00:34"};
        View view = LayoutInflater.from(this.recyclerView.getContext())
                .inflate(R.layout.recycleview_leaderboard_item, this.recyclerView, false);
        LeaderboardRecycleAdapter.ViewHolder firstHolder= new LeaderboardRecycleAdapter.ViewHolder(view);
        firstHolder.getPositionView().setText(tab[0]);
        firstHolder.getNameView().setText(tab[1]);
        firstHolder.getBestTimeView().setText(tab[2]);
        this.linearLayout.addView(view,1);
    }
}
