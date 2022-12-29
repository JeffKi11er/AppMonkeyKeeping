package com.example.appmonkeykeeping.center;


import android.util.Log;

import com.example.appmonkeykeeping.helper.DateHelper;
import com.example.appmonkeykeeping.model.Money;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        ArrayList<Money>monies = dbSystem.readListNoteMoneyData();
        return (monies.size()>0)?
                newestSort(monies):
                monies;
    }
    public void addMoneyNote(Money money){
        dbSystem.insertMoney(money);
    }
    public void updateMoneyNote(Money money){
        dbSystem.updateNoteMoney(money);
    }
    public void removeMoneyNote(long id){
        dbSystem.deleteNoteMoney(id);
    }
    public long maxIdDB(){
        return  dbSystem.getCategoryMaxId();
    }
    public Long[] totalAmount(){
        if(dbSystem.readListNoteMoneyData().size()==0){
            return new Long[]{ Long.valueOf(0), Long.valueOf(0)};
        }
        long totalOutcome = 0;
        long totalIncome = 0;
        for (Money money: dbSystem.readListNoteMoneyData()) {
            switch (money.getTag()){
                case "income":
                    totalIncome += money.getActualCost();
                    break;
                default:
                    totalOutcome += money.getActualCost();
                    break;
            }
        }
        return new Long[]{totalIncome,totalOutcome};
    }
    public Long[]moneyStackHolder(){
        long totalNecessityMaintain = 0;
        long totalFinanceMaintain = 0;
        long totalSavedMaintain = 0;
        long totalEducationMaintain = 0;
        long totalPlayMaintain = 0;
        long totalGiveMaintain = 0;
        long totalIncome = totalAmount()[0];
        totalNecessityMaintain = totalIncome * 55/100;
        totalFinanceMaintain = totalIncome * 10/100;
        totalSavedMaintain = totalIncome * 10/100;
        totalEducationMaintain = totalIncome * 10/100;
        totalPlayMaintain = totalIncome * 10/100;
        totalGiveMaintain = totalIncome * 5/100;
        for (Money money : dbSystem.readListNoteMoneyData()) {
            switch (money.getCategory()){
                case "Education":
                    totalEducationMaintain-=money.getActualCost();
                    break;
                case "Save":
                    totalSavedMaintain-=money.getActualCost();
                    break;
                case "Give":
                    totalGiveMaintain-=money.getActualCost();
                    break;
                case "Finance":
                    totalFinanceMaintain-=money.getActualCost();
                    break;
                case "Play":
                    totalPlayMaintain-=money.getActualCost();
                    break;
                case "Income":
                    break;
                default:
                    totalNecessityMaintain-=money.getActualCost();
                    break;
            }
        }
        return new Long[]{totalNecessityMaintain,
                totalFinanceMaintain,
                totalSavedMaintain,
                totalEducationMaintain,
                totalPlayMaintain,
                totalGiveMaintain};
    }
    public int []moneyProjectCategorizes() {
        double totalNecessity = 0;
        double totalFinance = 0;
        double totalSaved = 0;
        double totalEducation = 0;
        double totalPlay = 0;
        double totalGive = 0;
        double totalIncome = totalAmount()[0].doubleValue();
        totalNecessity = totalIncome * 55 / 100;
        totalFinance = totalIncome * 10 / 100;
        totalSaved = totalIncome * 10 / 100;
        totalEducation = totalIncome * 10 / 100;
        totalPlay = totalIncome * 10 / 100;
        totalGive = totalIncome * 5 / 100;
        double totalNecessityMaintain = moneyStackHolder()[0].doubleValue();
        double totalFinanceMaintain = moneyStackHolder()[1].doubleValue();
        double totalSavedMaintain = moneyStackHolder()[2].doubleValue();
        double totalEducationMaintain = moneyStackHolder()[3].doubleValue();
        double totalPlayMaintain = moneyStackHolder()[4].doubleValue();
        double totalGiveMaintain = moneyStackHolder()[5].doubleValue();
        int necessityProg = Math.round((float) ((totalNecessity-totalNecessityMaintain) / totalNecessity) * 100);
        int financeProg = Math.round((float) ((totalFinance-totalFinanceMaintain) / totalFinance) * 100);
        int savedProg = Math.round((float) ((totalSaved-totalSavedMaintain) / totalSaved) * 100);
        int educationProg = Math.round((float) ((totalEducation-totalEducationMaintain) / totalEducation) * 100);
        int playProg = Math.round((float) ((totalPlay-totalPlayMaintain) / totalPlay) * 100);
        int giveProg = Math.round((float) ((totalGive-totalGiveMaintain) / totalGive) * 100);
        Log.e(getClass().getName(),totalNecessityMaintain / totalNecessity+"-"+totalFinanceMaintain / totalFinance+"-"+totalSavedMaintain / totalSaved+"-"+
                totalEducationMaintain / totalEducation+"-"+totalPlayMaintain / totalPlay+"-"+totalGiveMaintain / totalGive);
        return new int[]{
                necessityProg,
                financeProg,
                savedProg,
                educationProg,
                playProg,
                giveProg
        };
    }
    public Long[]categoriesIncomeAndOutcome(){
        long totalNecessityMaintain = 0;
        long totalFinanceMaintain = 0;
        long totalSavedMaintain = 0;
        long totalEducationMaintain = 0;
        long totalPlayMaintain = 0;
        long totalGiveMaintain = 0;
        long totalIncome = totalAmount()[0];
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(new Date());
        int currentMonth = calendarNow.get(Calendar.MONTH);
        Log.e("current month",String.valueOf(currentMonth));
        Calendar calendarDateCheck = Calendar.getInstance();
        ArrayList<Money>data = tableOrganization.showList();
        for (int i = 0; i< data.size(); i++) {
            calendarDateCheck.setTime(Objects.requireNonNull(DateHelper.stringToDateByDataFormat(data.get(i).getDate())));
            if(currentMonth==calendarDateCheck.get(Calendar.MONTH)){
                switch (data.get(i).getCategory()) {
                    case "Education":
                        totalEducationMaintain += data.get(i).getActualCost();
                        break;
                    case "Save":
                        totalSavedMaintain += data.get(i).getActualCost();
                        break;
                    case "Give":
                        totalGiveMaintain += data.get(i).getActualCost();
                        break;
                    case "Finance":
                        totalFinanceMaintain += data.get(i).getActualCost();
                        break;
                    case "Play":
                        totalPlayMaintain += data.get(i).getActualCost();
                        break;
                    case "Income":
                        break;
                    default:
                        totalNecessityMaintain += data.get(i).getActualCost();
                        break;
                }
            }
        }
        return new Long[]{totalIncome,totalNecessityMaintain,
                totalFinanceMaintain,
                totalSavedMaintain,
                totalEducationMaintain,
                totalPlayMaintain,
                totalGiveMaintain};
    }
    public Long[]categoriesIncomeAndOutcomeForGrouping(ArrayList<Money>data){
        long totalNecessityMaintain = 0;
        long totalFinanceMaintain = 0;
        long totalSavedMaintain = 0;
        long totalEducationMaintain = 0;
        long totalPlayMaintain = 0;
        long totalGiveMaintain = 0;
        long totalIncome = totalAmount()[0];
        for (int i = 0; i< data.size(); i++) {
            switch (data.get(i).getCategory()) {
                case "Education":
                    totalEducationMaintain += data.get(i).getActualCost();
                    break;
                case "Save":
                    totalSavedMaintain += data.get(i).getActualCost();
                    break;
                case "Give":
                    totalGiveMaintain += data.get(i).getActualCost();
                    break;
                case "Finance":
                    totalFinanceMaintain += data.get(i).getActualCost();
                    break;
                case "Play":
                    totalPlayMaintain += data.get(i).getActualCost();
                    break;
                case "Income":
                    break;
                default:
                    totalNecessityMaintain += data.get(i).getActualCost();
                    break;
            }
        }
        return new Long[]{totalIncome,totalNecessityMaintain,
                totalFinanceMaintain,
                totalSavedMaintain,
                totalEducationMaintain,
                totalPlayMaintain,
                totalGiveMaintain};
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
