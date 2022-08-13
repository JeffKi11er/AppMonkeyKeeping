package com.example.appmonkeykeeping.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSupport extends RecyclerView.Adapter<AdapterSupport.MySupportHolder> {
    private Context context;
    private ArrayList<String>locationSuggest;
    @NonNull
    @Override
    public MySupportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MySupportHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MySupportHolder extends RecyclerView.ViewHolder{
        public MySupportHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
