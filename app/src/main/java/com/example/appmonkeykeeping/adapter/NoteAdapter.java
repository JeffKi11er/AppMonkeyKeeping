package com.example.appmonkeykeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.model.Money;


import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    private Context context;
    private ArrayList<Money>moneyNotes;

    public NoteAdapter(Context context, ArrayList<Money> moneyNotes) {
        this.context = context;
        this.moneyNotes = moneyNotes;
    }

    public void updateList(ArrayList<Money> moneyNotes) {
        this.moneyNotes = moneyNotes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record,parent,false);
        return new NoteAdapter.NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.tvLocation.setText(moneyNotes.get(position).getLocation());
        holder.tvAmount.setText("¥"+String.valueOf(moneyNotes.get(position).getActualCost()));
        holder.tvDateTime.setText(moneyNotes.get(position).getDate());
        switch (moneyNotes.get(position).getCategory()){
            case "Lunch":
                holder.imgCategory.setImageResource(R.drawable.lunch);
                break;
            case "Internet":
                holder.imgCategory.setImageResource(R.drawable.internet);
                break;
            case "Gas":
                holder.imgCategory.setImageResource(R.drawable.gas);
                break;
            case "Groceries":
                holder.imgCategory.setImageResource(R.drawable.groceries);
                break;
            case "Breakfast":
                holder.imgCategory.setImageResource(R.drawable.breakfast);
                break;
            case "Electricity":
                holder.imgCategory.setImageResource(R.drawable.electricity);
                break;
            case "Transport":
                holder.imgCategory.setImageResource(R.drawable.transport);
                break;
            case "Education":
                holder.imgCategory.setImageResource(R.drawable.education);
                break;
            case "Save":
                holder.imgCategory.setImageResource(R.drawable.saving);
                break;
            case "Give":
                holder.imgCategory.setImageResource(R.drawable.give_page);
                break;
            case "Finance":
                holder.imgCategory.setImageResource(R.drawable.finance);
                break;
            case "Play":
                holder.imgCategory.setImageResource(R.drawable.play_page);
                break;
            case "Income":
                holder.imgCategory.setImageResource(R.drawable.income_item);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return moneyNotes.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        private TextView tvDateTime;
        private TextView tvAmount;
        private TextView tvLocation;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_cate_item);
            tvAmount = itemView.findViewById(R.id.tv_amount_item);
            tvDateTime = itemView.findViewById(R.id.tv_date_item);
            tvLocation = itemView.findViewById(R.id.tv_location);
        }
    }
}
