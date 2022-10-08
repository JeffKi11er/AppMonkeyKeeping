package com.example.appmonkeykeeping.helper;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmManagerHelper {
    private static AlarmManagerHelper alarmManager;
    public static AlarmManagerHelper getInstance(){
        if(alarmManager==null){
            alarmManager = new AlarmManagerHelper();
        }
        return alarmManager;
    }
    public void startAlarm(Activity activity,Context context, Calendar calendar){
        AlarmManager alarmManager =  (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(activity,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,intent,0);
        if(calendar.before(Calendar.getInstance())){
            calendar.add(Calendar.DATE,1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }
    public void cancelAlarm(Context context){
        AlarmManager alarmManager =  (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,intent,0);
        alarmManager.cancel(pendingIntent);
    }
}
