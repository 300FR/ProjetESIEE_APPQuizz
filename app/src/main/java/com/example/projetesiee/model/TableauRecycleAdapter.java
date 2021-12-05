package com.example.projetesiee.model;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetesiee.R;
import com.example.projetesiee.controller.QuestionTableauActivity;

import java.util.ArrayList;

public class TableauRecycleAdapter extends RecyclerView.Adapter<TableauRecycleAdapter.ViewHolder>{



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.stone_image2);

        }

        public void setSize(int width,int height){
            imageView.getLayoutParams().width=QuestionTableauActivity.valWidth;
            imageView.getLayoutParams().height=QuestionTableauActivity.valHeight;
            //layoutParams.setMargins(0, 0, 0, 0);
            //imageView.setLayoutParams(layoutParams);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
    private ArrayList<Bitmap> localDataSet;

    public TableauRecycleAdapter(ArrayList<Bitmap> dataSet) {
        this.localDataSet = dataSet;

    }

    @NonNull
    @Override
    public TableauRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_stones_item, parent, false);
        TableauRecycleAdapter.ViewHolder adapter = new TableauRecycleAdapter.ViewHolder(view);
        adapter.setSize(this.localDataSet.get(0).getWidth(),this.localDataSet.get(0).getHeight());
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull TableauRecycleAdapter.ViewHolder holder, int position) {
        int index=position % localDataSet.size();
        holder.getImageView().setImageBitmap(Bitmap.createBitmap(localDataSet.get(index)));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }




}
