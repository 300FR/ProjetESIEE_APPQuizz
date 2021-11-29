package com.example.projetesiee.controller;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetesiee.R;
import com.example.projetesiee.model.Cadenas;
import com.example.projetesiee.model.TableauRecycleAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionTableauActivity extends AppCompatActivity {

    private Cadenas cadenas;
    private int ratioWidth;
    private int ratioHeight;
    private int ratioWidthDP;
    private int ratioHeightDP;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_question_tableau);

        RecyclerView[] recyclerViews = {
                findViewById(R.id.stones_combinaison_1),
                findViewById(R.id.stones_combinaison_2),
                findViewById(R.id.stones_combinaison_3),
                findViewById(R.id.stones_combinaison_4),
                findViewById(R.id.stones_combinaison_5),
                findViewById(R.id.stones_combinaison_6),
                findViewById(R.id.stones_combinaison_7),
                findViewById(R.id.stones_combinaison_8),
                findViewById(R.id.stones_combinaison_9),
                findViewById(R.id.stones_combinaison_10),
                findViewById(R.id.stones_combinaison_11),
                findViewById(R.id.stones_combinaison_12),
                findViewById(R.id.stones_combinaison_13),
                findViewById(R.id.stones_combinaison_14),
                findViewById(R.id.stones_combinaison_15),
                findViewById(R.id.stones_combinaison_16)};

        ArrayList<Bitmap> numbers = new ArrayList<>(Arrays.asList(setBitmaps()));
        TableauRecycleAdapter adapt = new TableauRecycleAdapter(numbers);

        int [] combinaison = new int[recyclerViews.length];
        for (int i=0;i<recyclerViews.length;i++){
            combinaison[i]=(i);
        }
        this.cadenas= new Cadenas(combinaison);


        for (int i=0;i<recyclerViews.length;i++) {
            final int index=i;
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            FrameLayout.LayoutParams layoutParams= new FrameLayout.LayoutParams( ratioWidthDP,ratioHeightDP);
            TableRow.LayoutParams layoutParamsParent= new TableRow.LayoutParams(ratioWidthDP+50,ratioHeightDP+50);

            RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
                @Override protected int getVerticalSnapPreference() {
                    return LinearSmoothScroller.SNAP_TO_START;
                }
                @Override
                protected int calculateTimeForScrolling(int dx) {
                    return super.calculateTimeForScrolling(dx*20);
                }
            };

            RecyclerView recyclerView = recyclerViews[i];
            recyclerView.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
                        if (pos==-1) return false;
                        runOnUiThread(new Runnable() { @Override public void run() {
                            smoothScroller.setTargetPosition(pos);
                            layoutManager.startSmoothScroll(smoothScroller);
                        } });
                        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @SuppressLint("ClickableViewAccessibility")
                            @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                switch (newState) {
                                    case SCROLL_STATE_IDLE:
                                        if (cadenas.setCode(index,pos%numbers.size())){
                                            Toast.makeText(QuestionTableauActivity.this, "fini!", Toast.LENGTH_SHORT).show();
                                        }
                                        recyclerView.removeOnScrollListener(this); break;
                                }   }
                        });
                        return true;
                    }
                }
                return false;
            });

            recyclerView.setLayoutManager(layoutManager);
            ViewParent parent = recyclerView.getParent();
            if (parent!=null && parent instanceof ScrollView){
                ((ScrollView) parent).setLayoutParams(layoutParamsParent);
            }
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setAdapter(adapt);

            layoutManager.scrollToPosition(100000000+this.cadenas.startingPosition[i]);
        }


    }


    private Bitmap[] setBitmaps(){
        Bitmap spriteSheet= BitmapFactory.decodeResource(getResources(), R.drawable.louis_xiv);
        int NBCol =4;
        int NBLine =4;
        Bitmap[] bitmaps= new Bitmap[NBCol*NBLine];
        ratioWidth=spriteSheet.getWidth()/(NBCol);
        ratioHeight=spriteSheet.getHeight()/(NBLine);
        ratioWidthDP =pxToDp(ratioWidth);
        ratioHeightDP = pxToDp(ratioHeight);


        for(int j=0; j<NBLine; j++) {
            for(int i=0; i<NBCol; i++) {
                bitmaps[j*NBCol+i] = Bitmap.createBitmap(spriteSheet, ratioWidth*i, ratioHeight*j,ratioWidth, ratioHeight);
            }
        }

        return bitmaps;


    }
    public int dpToPx(int dp) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
    public int pxToDp(int px) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) px / density);
    }
}
