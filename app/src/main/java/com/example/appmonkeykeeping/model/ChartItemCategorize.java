package com.example.appmonkeykeeping.model;

public class ChartItemCategorize {
    private int color;
    private String name;
    private Long numberMaintain;
    private Long numberOutcome;

    public ChartItemCategorize(int color, String name, Long numberMaintain, Long numberOutcome) {
        this.color = color;
        this.name = name;
        this.numberMaintain = numberMaintain;
        this.numberOutcome = numberOutcome;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumberMaintain() {
        return numberMaintain;
    }

    public void setNumberMaintain(Long numberMaintain) {
        this.numberMaintain = numberMaintain;
    }

    public Long getNumberOutcome() {
        return numberOutcome;
    }

    public void setNumberOutcome(Long numberOutcome) {
        this.numberOutcome = numberOutcome;
    }
}
