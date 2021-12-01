package com.example.projetesiee.model;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetesiee.R;

import java.util.ArrayList;

public class LeaderboardRecycleAdapter extends RecyclerView.Adapter<LeaderboardRecycleAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView position;
        private final TextView name;
        private final TextView best_time;

        public ViewHolder(View view) {
            super(view);
            position = (TextView) view.findViewById(R.id.leaderboard_position);
            name = (TextView) view.findViewById(R.id.leaderboard_name);
            best_time = (TextView) view.findViewById(R.id.leaderboard_best_time);

        }

        public TextView getPositionView() {
            return position;
        }
        public TextView getNameView() {
            return name;
        }
        public TextView getBestTimeView() {
            return best_time;
        }
    }

    private ArrayList<String[]> localDataSet;

    public LeaderboardRecycleAdapter(ArrayList<String[]> dataSet) {
        this.localDataSet = dataSet;

    }

    @NonNull
    @Override
    public LeaderboardRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_leaderboard_item, parent, false);
        return  new LeaderboardRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String [] tab = this.localDataSet.get(position);
        holder.getPositionView().setText(tab[0]);
        holder.getNameView().setText(tab[1]);
        holder.getBestTimeView().setText(tab[2]);
    }

    @Override
    public int getItemCount() {
        return this.localDataSet.size();
    }
}