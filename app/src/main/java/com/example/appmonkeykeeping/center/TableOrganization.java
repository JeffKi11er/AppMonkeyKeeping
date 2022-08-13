package com.example.appmonkeykeeping.center;


import static com.example.appmonkeykeeping.annotation.Annotation.DATE_FORMAT;

import android.util.Log;

import com.example.appmonkeykeeping.model.Money;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TableOrganization {
    private static TableOrganization tableOrganization;
    private List<String>dataLeak;
    public static TableOrganization getInstance(){
        if(tableOrganization == null){
            tableOrganization = new TableOrganization();
        }
        return tableOrganization;
    }
    private Date moneyDate(Money money){
        Date dateOfRecord = null;
        try {
            dateOfRecord = new SimpleDateFormat(DATE_FORMAT).parse(money.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateOfRecord;
    }
    private void dataShowing() {
        ArrayList<Money>data = new ArrayList<>();
        for (String money:dataLeak) {
            String[]dataCheck = money.split("\t");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,Integer.parseInt(dataCheck[0].split("/")[0]));
            calendar.set(Calendar.MONTH,Integer.parseInt(dataCheck[0].split("/")[1])-1);
            calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dataCheck[0].split("/")[2]));
            Money moneyData = new Money();
//            moneyData.setDate(calendar.getTime());
            data.add(moneyData);
        }
        ArrayList<Money>sortedDate = newestSort(data);
        for (Money money:
             sortedDate) {
            System.out.println(money.getDate());
        }
    }
    public ArrayList<Money> newestSort(ArrayList<Money>monies){
        List<Money>moneySort = new ArrayList<>();
        for (Money money:monies) {
            moneySort.add(money);
        }
        Collections.sort(moneySort, new sortItems());
        monies = new ArrayList<>();
        for (Money payback:moneySort) {
            monies.add(payback);
        }
        return monies;
    }
    private Money pointInDates(ArrayList<Money>monies,boolean isMax){
        Money temp = monies.get(0);
        Date datePoint = moneyDate(temp);
        for (int i = 1; i < monies.size(); i++) {
            if(isMax){
                if(datePoint.before(moneyDate(monies.get(i)))){
                    temp = monies.get(i);
                }
            }else {
                if(datePoint.after(moneyDate(monies.get(i)))){
                    temp = monies.get(i);
                    Log.e(getClass().getName(),temp.getDate()+"is the date older");
                }
            }
        }
        return temp;
    }
    class sortItems implements Comparator<Money> {
        public int compare(Money a, Money b)
        {
            return b.getDate().compareTo(a.getDate());
        }
    }
}
