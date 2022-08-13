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
        holder.tvType.setText(moneyNotes.get(position).getCategory());
        holder.tvAmount.setText(String.valueOf(moneyNotes.get(position).getActualCost()));
        holder.tvDateTime.setText(moneyNotes.get(position).getDate());
        switch (moneyNotes.get(position).getCategory()){
            case "Lunch":
                holder.imgCategory.setImageResource(R.drawable.lunch_box);
                break;
            case "Roser fee":
                holder.imgCategory.setImageResource(R.drawable.rose);
                break;
            case "Internet":
                holder.imgCategory.setImageResource(R.drawable.freelance);
                break;
            case "Gas":
                holder.imgCategory.setImageResource(R.drawable.gas);
                break;
            case "Groceries":
                holder.imgCategory.setImageResource(R.drawable.grocery_cart);
                break;
            case "Breakfast":
                holder.imgCategory.setImageResource(R.drawable.coffee_cup);
                break;
            case "Entertainment":
                holder.imgCategory.setImageResource(R.drawable.popcorn);
                break;
            case "Electricity":
                holder.imgCategory.setImageResource(R.drawable.electrical_energy);
                break;
            case "Transport":
                holder.imgCategory.setImageResource(R.drawable.transportation);
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
        private TextView tvType;
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_cate_item);
            tvAmount = itemView.findViewById(R.id.tv_amount_item);
            tvDateTime = itemView.findViewById(R.id.tv_date_item);
            tvType = itemView.findViewById(R.id.tv_grocery_item);
        }
    }
}
