package com.example.appmonkeykeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.remote.ItemClickListener;

import java.util.ArrayList;

public class DetailOutcomeAdapter extends RecyclerView.Adapter<DetailOutcomeAdapter.IncomeHolder> {
    private Context context;
    private ArrayList<String>outcomeTags;
    private ItemClickListener listener;

    public DetailOutcomeAdapter(Context context, ArrayList<String> outcomeTags) {
        this.context = context;
        this.outcomeTags = outcomeTags;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public IncomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_outcome,parent,false);
        return new IncomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeHolder holder, int position) {
        int progress = 90;
        holder.tvOutcomeTag.setText(outcomeTags.get(position));
        holder.progress.setProgress(progress);
        holder.tvProgress.setText(progress+"%");
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
        return outcomeTags.size();
    }

    public class IncomeHolder extends RecyclerView.ViewHolder{
        TextView tvOutcomeTag;
        ProgressBar progress;
        TextView tvProgress;
        public IncomeHolder(@NonNull View itemView) {
            super(itemView);
            tvOutcomeTag = itemView.findViewById(R.id.tv_outcome_tag);
            progress = itemView.findViewById(R.id.progress_outcome);
            tvProgress = itemView.findViewById(R.id.tv_progress);
        }
    }
}
