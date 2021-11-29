package com.example.projetesiee.controller;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projetesiee.R;
import com.example.projetesiee.model.Cadenas;
import com.example.projetesiee.model.CadenasRecycleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionCadenasActivity extends AppCompatActivity{

    private Cadenas cadenas;
    public boolean temp=false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_question_cadenas);

        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("0", "1", "2","3", "4", "5","6", "7", "8","9"));

        this.cadenas= new Cadenas(new int[]{2,4,5,9});

        RecyclerView[] recyclerViews = {findViewById(R.id.cadenas_combinaison_1),
                findViewById(R.id.cadenas_combinaison_2),
                findViewById(R.id.cadenas_combinaison_3),
                findViewById(R.id.cadenas_combinaison_4) };

        CadenasRecycleAdapter adapt = new CadenasRecycleAdapter(numbers);

        for (int i=0;i<recyclerViews.length;i++) {
            final int index=i;
            LinearLayoutManager layout = new LinearLayoutManager(this){

            };
            RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
                @Override protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
                @Override
                protected int calculateTimeForScrolling(int dx) {
                    return super.calculateTimeForScrolling(dx*30);
                }
            };

            RecyclerView recyclerView = recyclerViews[i];
            recyclerView.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        int pos = layout.findLastCompletelyVisibleItemPosition();
                        if (pos==-1) return false;
                        runOnUiThread(new Runnable() { @Override public void run() {
                            smoothScroller.setTargetPosition(pos);
                            layout.startSmoothScroll(smoothScroller);
                        } });
                        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                switch (newState) {
                                    case SCROLL_STATE_IDLE:
                                        cadenas.setCode(index,pos%numbers.size());
                                        recyclerView.removeOnScrollListener(this); break;
                                }   }
                        });
                        return true;
                    }
                }
                return false;
            });

            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(adapt);

            layout.scrollToPosition(100000000+this.cadenas.startingPosition[i]);
        }
    }
}
