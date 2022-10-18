package com.example.appmonkeykeeping.center;

import android.util.Log;

import com.example.appmonkeykeeping.model.Money;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class DatabaseSystem {
    private static DatabaseSystem databaseSystem;
    private Realm realm;

    public static DatabaseSystem getInstance(){
        if(databaseSystem == null){
            databaseSystem = new DatabaseSystem();
        }
        return databaseSystem;
    }

    public void realmInitialize() {
        realm = Realm.getDefaultInstance();
    }
    public Long getCategoryMaxId(){
        long nextId ;
        Number currentId = realm.where(Money.class).max("id");
        if(currentId == null){
            nextId = 1;
        }else {
            nextId = currentId.intValue() + 1;
        }
        return nextId;
    }
    public void insertMoney(Money money){
        Log.e(getClass().getName(),"Inserting");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(money);
            }
        });
    }
    public void updateNoteMoney(Money money){
        Log.e(getClass().getName(),"Updating");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(money);
            }
        });
    }
    public Money findNoteMoneyWithId(long id){
        Money moneyInProcess = realm.where(Money.class).equalTo("id",id).findFirst();
        return moneyInProcess;
    }
    public void deleteNoteMoney(long id){
        Money moneyInProcess = realm.where(Money.class).equalTo("id",id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                moneyInProcess.deleteFromRealm();
            }
        });
    }
    public ArrayList<Money> readListNoteMoneyData(){
        ArrayList<Money>moneyList = new ArrayList<>();
        List<Money>moneyModels = realm.where(Money.class).findAll();
        for (int i = 0; i < moneyModels.size(); i++) {
            moneyList.add(moneyModels.get(i));
        }
        return moneyList;
    }
}
