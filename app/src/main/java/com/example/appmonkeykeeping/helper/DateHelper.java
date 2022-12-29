package com.example.appmonkeykeeping.helper;

import static com.example.appmonkeykeeping.annotation.AnnotationCode.DATE_FORMAT;
import static com.example.appmonkeykeeping.annotation.AnnotationCode.INSERT_SHOWING_FORMAT;

import android.annotation.SuppressLint;

import com.example.appmonkeykeeping.center.TableOrganization;
import com.example.appmonkeykeeping.model.Money;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateHelper {
    @SuppressLint("SimpleDateFormat")
    public static String formatDateData(Calendar calendar){
        SimpleDateFormat dateFormatInsert = new SimpleDateFormat(DATE_FORMAT);
        return dateFormatInsert.format(calendar.getTime());
    }
    @SuppressLint("SimpleDateFormat")
    public static String formatShowingDate(Calendar calendar){
        SimpleDateFormat dateFormatInsert = new SimpleDateFormat(INSERT_SHOWING_FORMAT);
        return dateFormatInsert.format(calendar.getTime());
    }
    @SuppressLint("SimpleDateFormat")
    public static Date stringToDateByDataFormat(String date){
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Calendar firstDayOfMonth(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.MONTH,Integer.parseInt(month)-1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar;
    }
    public static ArrayList<Long[]> groupNoteByMonth(){
        TableOrganization tableOrganization = TableOrganization.getInstance();
        ArrayList<Money> moneyArrayList = tableOrganization.showList();
        Calendar calendar = Calendar.getInstance();
        int monthCheck = 0;
        ArrayList<ArrayList<Money>>group = new ArrayList<>();
        for (int i = 0; i < moneyArrayList.size(); i++) {
            calendar.setTime(Objects.requireNonNull(DateHelper.stringToDateByDataFormat(moneyArrayList.get(i).getDate())));
            if(monthCheck!=calendar.get(Calendar.MONTH)){
                monthCheck = calendar.get(Calendar.MONTH);
                ArrayList<Money>arrayNote = new ArrayList<>();
                group.add(arrayNote);
                arrayNote.add(moneyArrayList.get(i));
            }else{
                if(group.size()>0){
                    group.get(group.size()-1).add(moneyArrayList.get(i));
                }
            }
        }
        ArrayList<Long[]>groupChart = new ArrayList<>();
        for (ArrayList<Money>groupMoney:group) {
            groupChart.add(tableOrganization.categoriesIncomeAndOutcomeForGrouping(groupMoney));
        }
        return groupChart;
    }
    public static ArrayList<Integer> monthGrouping(){
        TableOrganization tableOrganization = TableOrganization.getInstance();
        ArrayList<Money> moneyArrayList = tableOrganization.showList();
        Calendar calendar = Calendar.getInstance();
        int monthCheck = 0;
        ArrayList<Integer>group = new ArrayList<>();
        for (int i = 0; i < moneyArrayList.size(); i++) {
            calendar.setTime(Objects.requireNonNull(DateHelper.stringToDateByDataFormat(moneyArrayList.get(i).getDate())));
            if(monthCheck!=calendar.get(Calendar.MONTH)){
                monthCheck = calendar.get(Calendar.MONTH);
                group.add(monthCheck);
            }
        }
        return group;
    }
    public static String lastDay(Calendar calendar){
        return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    }
    public static String firstDay(Calendar calendar){
        return String.valueOf(calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    }
}
