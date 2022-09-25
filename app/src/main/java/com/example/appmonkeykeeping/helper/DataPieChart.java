package com.example.appmonkeykeeping.helper;

public class DataPieChart {
    private String label;
    private float data;

    public DataPieChart(String label, float data) {
        this.label = label;
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public float getData() {
        return data;
    }
}
