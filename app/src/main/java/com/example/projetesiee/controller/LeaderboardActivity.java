package com.example.projetesiee.controller;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.example.projetesiee.model.UtilGame;

import java.util.ArrayList;
import java.util.Collections;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

        dbOpenHelper = new DBOpenHelper(this);
        ArrayList<Object[]> list = dbOpenHelper.getLeaderboard();
        if(list.size()!=0){
            ArrayList<String[]> dataSet= new ArrayList<>();

            Collections.sort(list, new Comparator<Object[]>() {
                public int compare(Object[] o1, Object[] o2) {
                    Integer x=(int)o1[1];
                    Integer y=(int)o2[1];
                    return x.compareTo(y);
                }
            });

            for (int i=1;i<list.size();i++){
                int time=(int)list.get(i)[1];
                String displayTime=UtilGame.displayTime(time);
                if (time==0)  displayTime="";
                dataSet.add(new String[]{""+(i+1),""+list.get(i)[0], displayTime});
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            this.recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new LeaderboardRecycleAdapter(dataSet));
            layoutManager.scrollToPosition(0);

            String[] tab = new String[]{"1",""+list.get(0)[0], UtilGame.displayTime((int)list.get(0)[1])};

            View view = LayoutInflater.from(this.recyclerView.getContext())
                    .inflate(R.layout.recycleview_leaderboard_item, this.recyclerView, false);
            LeaderboardRecycleAdapter.ViewHolder firstHolder= new LeaderboardRecycleAdapter.ViewHolder(view);
            firstHolder.getPositionView().setText(tab[0]);
            firstHolder.getNameView().setText(tab[1]);
            firstHolder.getBestTimeView().setText(tab[2]);
            this.linearLayout.addView(view,1);
        }

    }
}
