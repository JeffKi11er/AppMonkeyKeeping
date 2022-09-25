package com.example.appmonkeykeeping.helper;

public class DataBarChart {
    private float amount;
    private float data;

    public DataBarChart(float amount, float data) {
        this.amount = amount;
        this.data = data;
    }

    public float getAmount() {
        return amount;
    }

    public float getData() {
        return data;
    }
}
