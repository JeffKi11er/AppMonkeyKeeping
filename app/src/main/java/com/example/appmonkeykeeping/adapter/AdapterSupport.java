package com.example.appmonkeykeeping.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.remote.ItemClickListener;

import java.util.ArrayList;

public class AdapterSupport extends RecyclerView.Adapter<AdapterSupport.MySupportHolder> {
    private Context context;
    private ArrayList<String>locationSuggest;
    private ItemClickListener listener;
    public AdapterSupport(Context context, ArrayList<String> locationSuggest) {
        this.context = context;
        this.locationSuggest = locationSuggest;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MySupportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_suggested,parent,false);
        return new MySupportHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySupportHolder holder, int position) {
        holder.tvLocationName.setText(locationSuggest.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onClick(holder.getAbsoluteAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationSuggest.size();
    }

    public class MySupportHolder extends RecyclerView.ViewHolder{
        TextView tvLocationName;
        public MySupportHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tv_location_saved);
        }
    }
}
