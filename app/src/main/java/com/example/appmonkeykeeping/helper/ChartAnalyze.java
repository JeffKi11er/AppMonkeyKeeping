package com.example.appmonkeykeeping.helper;

import android.graphics.Color;

import com.example.appmonkeykeeping.model.Money;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartAnalyze {
    private ArrayList<BarEntry>barEntries;
    private ArrayList<PieEntry>pieEntries;
    private static ChartAnalyze chartAnalyze;
    public static ChartAnalyze getInstance(){
        if(chartAnalyze==null){
            chartAnalyze = new ChartAnalyze();
        }
        return chartAnalyze;
    }
    public BarData setUpDataBar(ArrayList<Long>monies, String description, int[]colors){
        barEntries = new ArrayList<>();
        for (int i = 0; i < monies.size(); i++) {
            BarEntry barEntry = new BarEntry(i,(float)monies.get(i));
            barEntries.add(barEntry);
        }
        BarDataSet barDataSet = new BarDataSet(barEntries,description);
        barDataSet.setColors(colors);
        barDataSet.setDrawValues(false);
        return new BarData(barDataSet);
    }
    public void setAnimateBar(BarChart barChart,String description){
        barChart.animateY(1000);
        barChart.getDescription().setText(description);
        barChart.getDescription().setTextColor(Color.BLUE);
    }
    public void setAnimatePie(PieChart pieChart, String description){
        pieChart.animateY(1000);
        pieChart.animateX(1000);
        pieChart.getDescription().setText(description);
        pieChart.getDescription().setTextColor(Color.BLUE);
    }
    public PieData setUpDataPie(ArrayList<Long>monies,String description, int[]colors){
        pieEntries = new ArrayList<>();
        for (int i = 0; i < monies.size(); i++) {
            PieEntry pieEntry = new PieEntry(i,monies.get(i));
            pieEntries.add(pieEntry);
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries,description);
        pieDataSet.setColors(colors);
        pieDataSet.setDrawValues(false);
        return new PieData(pieDataSet);
    }
}
