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
    private DatabaseSystem dbSystem;

    public TableOrganization() {
        initializeDatabase();
    }

    public static TableOrganization getInstance(){
        if(tableOrganization == null){
            tableOrganization = new TableOrganization();
        }
        return tableOrganization;
    }
    public void initializeDatabase() {
        dbSystem = DatabaseSystem.getInstance();
        dbSystem.realmInitialize();
    }
    public ArrayList<Money> showList(){
        ArrayList<Money>monies = dbSystem.readListData();
        return (monies.size()>0)?
                newestSort(monies):
                monies;
    }
    public void addMoneyNote(Money money){
        dbSystem.insert(money);
    }
    public void updateMoneyNote(Money money){
        dbSystem.update(money);
    }
    public void removeMoneyNote(long id){
        dbSystem.delete(id);
    }
    public long maxIdDB(){
        return  dbSystem.getMaxId();
    }
    public Long[] totalAmount(){
        if(dbSystem.readListData().size()==0){
            return new Long[]{ Long.valueOf(0), Long.valueOf(0)};
        }
        long totalOutcome = 0;
        long totalIncome = 0;
        for (Money money: dbSystem.readListData()) {
            switch (money.getTag()){
                case "income":
                    totalIncome += money.getActualCost();
                    break;
                case "outcome":
                    totalOutcome += money.getActualCost();
                    break;
            }
            Log.e(getClass().getName(),money.toString());
        }
        return new Long[]{totalIncome,totalOutcome};
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
    class sortItems implements Comparator<Money> {
        public int compare(Money a, Money b)
        {
            return b.getDate().compareTo(a.getDate());
        }
    }
}
