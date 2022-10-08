package com.example.appmonkeykeeping.helper;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.appmonkeykeeping.R;
import com.example.appmonkeykeeping.annotation.AnnotationCode;

import java.util.Calendar;

public class NotificationHelper {
    private static NotificationHelper notificationHelper;
    public static NotificationHelper getInstance(){
        if (notificationHelper == null){
            notificationHelper = new NotificationHelper();
        }
        return notificationHelper;
    }
    public void sendOnChannel(Context context,String message,int id){
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat
                .Builder(context,AnnotationCode.ALARM_PERIOD)
                .setSmallIcon(R.drawable.monkey)
                .setContentTitle(message)
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();
        notificationManager.notify(id,notification);
    }
}
