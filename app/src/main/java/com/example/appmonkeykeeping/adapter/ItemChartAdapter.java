package com.example.appmonkeykeeping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.model.ChartItemCategorize;

import java.util.ArrayList;

public class ItemChartAdapter extends RecyclerView.Adapter<ItemChartAdapter.ChartHolder> {
    private Context context;
    private ArrayList<ChartItemCategorize>chartItemCategorizes;

    public ItemChartAdapter(Context context, ArrayList<ChartItemCategorize> chartItemCategorizes) {
        this.context = context;
        this.chartItemCategorizes = chartItemCategorizes;
    }

    @NonNull
    @Override
    public ChartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_dashboard,parent,false);
        return new ChartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartHolder holder, int position) {
        holder.tvName.setText(chartItemCategorizes.get(position).getName());
        holder.tvMaintain.setText(chartItemCategorizes.get(position).getNumberMaintain().toString());
        holder.tvOutcome.setText(chartItemCategorizes.get(position).getNumberOutcome().toString());
        holder.cardColor.setCardBackgroundColor(chartItemCategorizes.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return chartItemCategorizes.size();
    }

    public class ChartHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        private CardView cardColor;
        private TextView tvMaintain;
        private TextView tvOutcome;
        public ChartHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_tag_chart);
            tvMaintain = itemView.findViewById(R.id.tv_maintain_item_chart);
            tvOutcome = itemView.findViewById(R.id.tv_outcome_item_chart);
            cardColor = itemView.findViewById(R.id.card_chart);
        }
    }
}
