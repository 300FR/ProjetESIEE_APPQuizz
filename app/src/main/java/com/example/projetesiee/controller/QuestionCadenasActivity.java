package com.example.projetesiee.controller;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.example.projetesiee.model.UtilGame.SEPARATOR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetesiee.R;
import com.example.projetesiee.model.Cadenas;
import com.example.projetesiee.model.CadenasRecycleAdapter;
import com.example.projetesiee.model.UtilGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuestionCadenasActivity extends QuestionActivity{

    private Cadenas cadenas;
    private TextView indications;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_cadenas);

        this.indications=findViewById(R.id.cadenas_indication_text);

        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("0", "1", "2","3", "4", "5","6", "7", "8","9"));

        String data = getIntent().getStringExtra(QuestionBombeActivity.class.getName());
        int code_0 =-1;
        String text_0 = "";
        if (data!=null && !data.isEmpty()){
           code_0=Integer.parseInt(data.split(SEPARATOR)[1]);
           text_0=data.split(SEPARATOR)[0];
        }
        String indic ="";
        int choice = new Random().nextInt(2);
        if (code_0==-1 || choice==0){
            code_0= new Random().nextInt(10);;
            indic+=getString(R.string.cadenas_eq,""+(2+code_0),""+(4+code_0),""+(3+code_0));
        }else{
            indic+=getString(R.string.cadenas_fil,text_0);
        }
        indic+=getString(R.string.cadenas_trois);
        this.indications.setText(indic);


        this.cadenas= new Cadenas(new int[]{0,4,5,code_0},10);

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
                                        if (cadenas.setCode(index,pos%numbers.size())){
                                            Intent intent = new Intent(QuestionCadenasActivity.this, QuestionSuccessActivity.class);
                                            intent.putExtra(UtilGame.KEY_CURRENT_TIME, "" + startTime+currentTime);
                                            startActivity(intent);
                                        }
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
        super.startTimer();
        super.setContext(this);
    }

    @Override
    protected void runInTimer() {
        super.runInTimer();
        int[] codeSecret = new int[3];
        int index=0;
        for (int i=0; i<super.currentTimeText.length();i++){
            String s=super.currentTimeText.substring(i,i+1);
            if (s.equals(":")) continue;
            if (i>=4) break;
            codeSecret[index++]=Integer.parseInt(s);
        }

        this.cadenas.setCodeSecret(codeSecret);
    }
}
