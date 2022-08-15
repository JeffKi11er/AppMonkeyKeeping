package com.example.appmonkeykeeping;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MonkeyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
