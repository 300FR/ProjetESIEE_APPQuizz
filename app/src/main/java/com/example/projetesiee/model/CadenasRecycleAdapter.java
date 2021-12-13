package com.example.projetesiee.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetesiee.R;

import java.util.ArrayList;


public class CadenasRecycleAdapter extends RecyclerView.Adapter<CadenasRecycleAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.cadenas_text_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }
    private ArrayList<String> localDataSet;

    public CadenasRecycleAdapter(ArrayList<String> dataSet) {
        this.localDataSet = dataSet;
    }

    @NonNull
    @Override
    public CadenasRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_cadenas_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CadenasRecycleAdapter.ViewHolder holder, int position) {
        int index=position % localDataSet.size();
        holder.getTextView().setText(localDataSet.get(index));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
