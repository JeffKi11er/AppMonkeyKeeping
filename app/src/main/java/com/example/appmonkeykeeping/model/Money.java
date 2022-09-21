package com.example.appmonkeykeeping.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Money extends RealmObject {
    @PrimaryKey
    private long id;
    private String date;
    private String category;
    private String detail;
    private String location;
    private boolean usePeriod;
    private long projectCost;
    private long actualCost;
    private long diff;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isUsePeriod() {
        return usePeriod;
    }

    public void setUsePeriod(boolean usePeriod) {
        this.usePeriod = usePeriod;
    }

    public long getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(long projectCost) {
        this.projectCost = projectCost;
    }

    public long getActualCost() {
        return actualCost;
    }

    public void setActualCost(long actualCost) {
        this.actualCost = actualCost;
    }

    public long getDiff() {
        return diff;
    }

    public void setDiff(long diff) {
        this.diff = diff;
    }

    @Override
    public String toString() {
        return  id+"\t"+date +"\t"+category+"\t"+detail+"\t"+location+"\t"+usePeriod+"\t"+projectCost+"\t"+actualCost+"\t"+diff+"\t"+tag;
    }
}
