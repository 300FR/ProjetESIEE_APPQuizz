package com.example.projetesiee.controller;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableRow;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetesiee.R;
import com.example.projetesiee.model.Cadenas;
import com.example.projetesiee.model.TableauRecycleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuestionTableauActivity extends QuestionActivity {

    private Cadenas cadenas;
    private int ratioWidth;
    private int ratioHeight;
    private ImageView preview;

    private float ratio;

    public static int valWidth;
    public static int valHeight;

    public static float DensityOfApp;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_tableau);
        this.preview = findViewById(R.id.image_preview_tableau);

        RecyclerView[] recyclerViews = {
                findViewById(R.id.tableau_combinaison_1),
                findViewById(R.id.tableau_combinaison_2),
                findViewById(R.id.tableau_combinaison_3),
                findViewById(R.id.tableau_combinaison_4),
                findViewById(R.id.tableau_combinaison_5),
                findViewById(R.id.tableau_combinaison_6),
                findViewById(R.id.tableau_combinaison_7),
                findViewById(R.id.tableau_combinaison_8),
                findViewById(R.id.tableau_combinaison_9),
                findViewById(R.id.tableau_combinaison_10),
                findViewById(R.id.tableau_combinaison_11),
                findViewById(R.id.tableau_combinaison_12),
                findViewById(R.id.tableau_combinaison_13),
                findViewById(R.id.tableau_combinaison_14),
                findViewById(R.id.tableau_combinaison_15),
                findViewById(R.id.tableau_combinaison_16)};
        setRecyclersViews(recyclerViews);

        super.startTimer();
        super.setContext(QuestionTableauActivity.this);
    }

    private Bitmap[] setBitmaps(){
        int[] images = new int[]{ R.drawable.louis_xiv, R.drawable.joconde,R.drawable.arcimboldo,
                R.drawable.charette,R.drawable.napoleon,R.drawable.francois_1er,R.drawable.la_laitiere};
        int indexChoisi = (new Random()).nextInt(images.length);
        this.preview.setImageResource(images[indexChoisi]);
        Bitmap spriteSheet= BitmapFactory.decodeResource(getResources(),images[indexChoisi]);
        int NBCol =4;
        int NBLine =4;
        Bitmap[] bitmaps= new Bitmap[NBCol*NBLine];
        ratioWidth=spriteSheet.getWidth()/(NBCol);
        ratioHeight=spriteSheet.getHeight()/(NBLine);
        ratio=(float)ratioHeight/(float)ratioWidth;

        for(int j=0; j<NBLine; j++) {
            for(int i=0; i<NBCol; i++) {
                bitmaps[j*NBCol+i] = Bitmap.createBitmap(spriteSheet, ratioWidth*i, ratioHeight*j,ratioWidth, ratioHeight);
            }
        }
        return bitmaps;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setRecyclersViews(RecyclerView[] recyclerViews){
        ArrayList<Bitmap> numbers = new ArrayList<>(Arrays.asList(setBitmaps()));
        TableauRecycleAdapter adapt = new TableauRecycleAdapter(numbers);

        float density = this.getResources().getDisplayMetrics().density;
        DensityOfApp=density;

        int [] combinaison = new int[recyclerViews.length];
        for (int i=0;i<recyclerViews.length;i++){
            combinaison[i]=(i);
        }
        this.cadenas= new Cadenas(combinaison,recyclerViews.length);

        for (int i=0;i<recyclerViews.length;i++) {
            final int index=i;
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            int w=230;
            valWidth= w;
            valHeight= (int)(w*ratio);
            TableRow.LayoutParams layoutParamsParent= new TableRow.LayoutParams(valWidth,valHeight);

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
                        final int pos = layoutManager.findLastVisibleItemPosition();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                smoothScroller.setTargetPosition(pos);
                                layoutManager.startSmoothScroll(smoothScroller);
                                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @SuppressLint("ClickableViewAccessibility")
                                    @Override
                                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    switch (newState) {
                                        case SCROLL_STATE_IDLE:
                                            if (cadenas.setCode(index, pos % numbers.size())) {
                                                MoveToNextQuestion(QuestionTableauActivity.this);
                                            }
                                            recyclerView.removeOnScrollListener(this);
                                            break;
                                        default:
                                            break;
                                    }
                                    }
                                });
                            }
                        });

                        return true;
                    }
                    case MotionEvent.ACTION_DOWN:
                        recyclerView.clearOnScrollListeners();
                        return true;
                    default:
                        return false;
                }
            });

            recyclerView.setLayoutManager(layoutManager);
            ViewParent parent = recyclerView.getParent();
            if (parent!=null && parent instanceof FrameLayout){
                ((FrameLayout) parent).setLayoutParams(layoutParamsParent);
            }

            recyclerView.setAdapter(adapt);

            //layoutManager.scrollToPosition(100000000+this.cadenas.startingPosition[i]);
            layoutManager.scrollToPosition(100000000+i+1);
        }
    }
}
